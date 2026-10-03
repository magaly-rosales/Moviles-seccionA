package com.tecsup.mibodega.ui.cliente

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.TipoEntrega
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.componentes.DestinoBarra
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val INICIO = "inicio"
    const val FAVORITOS = "favoritos"
    const val PEDIDOS = "pedidos"
    const val PERFIL = "perfil"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

private const val DURACION_ANIMACION = 300

@Composable
fun ClienteApp(
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var tipoEntrega by remember { mutableStateOf(TipoEntrega.DELIVERY) }
    val alternarFavorito: (Producto) -> Unit = { producto ->
        favoritos = if (producto.id in favoritos) favoritos - producto.id else favoritos + producto.id
    }

    val navegarDestino: (DestinoBarra) -> Unit = { destino ->
        val ruta = when (destino) {
            DestinoBarra.INICIO -> Rutas.INICIO
            DestinoBarra.FAVORITOS -> Rutas.FAVORITOS
            DestinoBarra.PEDIDOS -> Rutas.PEDIDOS
            DestinoBarra.PERFIL -> Rutas.PERFIL
        }
        navController.navigate(ruta) {
            popUpTo(Rutas.INICIO)
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURACION_ANIMACION)
            ) + fadeIn(tween(DURACION_ANIMACION))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURACION_ANIMACION)
            ) + fadeOut(tween(DURACION_ANIMACION))
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURACION_ANIMACION)
            ) + fadeIn(tween(DURACION_ANIMACION))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURACION_ANIMACION)
            ) + fadeOut(tween(DURACION_ANIMACION))
        }
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = { navController.navigate(Rutas.LOGIN) },
                onTerminos = { /* TODO: abrir términos y condiciones */ }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    // TODO: guardar estos datos cuando exista el registro real
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onVolver = { navController.popBackStack() },
                onLoginExitoso = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                favoritos = favoritos,
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onFavorito = alternarFavorito,
                onNavegar = navegarDestino
            )
        }

        composable(Rutas.FAVORITOS) {
            FavoritosScreen(
                favoritos = listaProductosFake.filter { it.id in favoritos },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onFavorito = alternarFavorito,
                onNavegar = navegarDestino
            )
        }

        composable(Rutas.PEDIDOS) {
            PedidosScreen(
                pedidos = pedidos,
                onNavegar = navegarDestino
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                modoOscuro = modoOscuro,
                onCambiarModoOscuro = onCambiarModoOscuro,
                onNavegar = navegarDestino
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                esFavorito = producto.id in favoritos,
                onVolver = { navController.popBackStack() },
                onFavorito = { alternarFavorito(producto) },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null // si llega a 0, se elimina de la lista
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = { tipoElegido ->
                    tipoEntrega = tipoElegido
                    navController.navigate(Rutas.ENTREGA)
                }
            )
        }

        composable(Rutas.ENTREGA) {
            DatosEntregaScreen(
                onVolver = { navController.popBackStack() },
                onConfirmar = {
                    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
                    val nuevoPedido = Pedido(
                        numero = pedidos.size + 1,
                        fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
                        items = carrito,
                        tipoEntrega = tipoEntrega,
                        total = subtotal + tipoEntrega.costo
                    )

                    pedidos = listOf(nuevoPedido) + pedidos
                    carrito = emptyList()

                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.INICIO)
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                onVolverInicio = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                    }
                }
            )
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}