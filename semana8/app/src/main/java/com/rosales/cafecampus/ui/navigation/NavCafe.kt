package com.rosales.cafecampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rosales.cafecampus.CafeApp
import com.rosales.cafecampus.model.Rol
import com.rosales.cafecampus.ui.cliente.*
import com.rosales.cafecampus.ui.theme.CafeTheme
import com.rosales.cafecampus.viewmodel.*

data class TabItem(val ruta: String, val etiqueta: String, val icono: ImageVector)

private val tabsCliente = listOf(
    TabItem(Rutas.MENU, "Menú", Icons.Filled.RestaurantMenu),
    TabItem(Rutas.MIS_PEDIDOS, "Pedidos", Icons.Filled.Receipt),
    TabItem(Rutas.PERFIL, "Perfil", Icons.Filled.Person)
)

@Composable
fun NavCafe(app: CafeApp) {
    val nav = rememberNavController()
    val factory = remember { CafeViewModelFactory(app.repository, app.prefs) }
    val sesionVm: SesionViewModel = viewModel(factory = factory)
    val menuVm: MenuViewModel = viewModel(factory = factory)
    val pedidosClienteVm: PedidosClienteViewModel = viewModel(factory = factory)

    val perfil by sesionVm.perfilActivo.collectAsState()
    val oscuro by sesionVm.modoOscuro.collectAsState()
    val rolTema = if (perfil == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE

    val entrada by nav.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route

    CafeTheme(rol = rolTema, oscuro = oscuro) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(Modifier.systemBarsPadding()) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        if (tabsCliente.any { it.ruta == rutaActual }) {
                            BarraInferior(tabsCliente, rutaActual) { ruta -> irATab(nav, ruta, Rutas.MENU) }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = nav,
                        startDestination = Rutas.SPLASH,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Rutas.SPLASH) {
                            SplashScreen {
                                val destino = when (sesionVm.perfilActivo.value) {
                                    Rol.CLIENTE.name -> Rutas.MENU
                                    Rol.ADMIN.name -> Rutas.PANEL
                                    else -> Rutas.LOGIN
                                }
                                nav.navigate(destino) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                            }
                        }

                        composable(Rutas.LOGIN) {
                            LoginScreen(sesionVm) { rol ->
                                val destino = if (rol == Rol.ADMIN) Rutas.PANEL else Rutas.MENU
                                nav.navigate(destino) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                            }
                        }

                        // ---------- Cliente ----------
                        composable(Rutas.MENU) {
                            MenuScreen(
                                vm = menuVm,
                                onProducto = { id -> nav.navigate(Rutas.detalleProducto(id)) },
                                onCarrito = { nav.navigate(Rutas.CONFIRMAR) }
                            )
                        }

                        composable(
                            Rutas.DETALLE_PRODUCTO,
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { e ->
                            DetalleProductoScreen(
                                vm = menuVm,
                                productoId = e.arguments?.getInt("id") ?: 0,
                                onAtras = { nav.popBackStack() },
                                onAgregado = { nav.popBackStack() }
                            )
                        }

                        composable(Rutas.CONFIRMAR) {
                            ConfirmarPedidoScreen(
                                menuVm = menuVm,
                                pedidosVm = pedidosClienteVm,
                                onAtras = { nav.popBackStack() },
                                onSeguirPidiendo = { nav.popBackStack() },
                                onConfirmado = { id ->
                                    menuVm.vaciar()
                                    nav.navigate(Rutas.seguimiento(id)) { popUpTo(Rutas.MENU) }
                                }
                            )
                        }

                        composable(
                            Rutas.SEGUIMIENTO,
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { e ->
                            SeguimientoScreen(
                                vm = pedidosClienteVm,
                                pedidoId = e.arguments?.getInt("id") ?: 0,
                                onAtras = { nav.popBackStack() }
                            )
                        }

                        composable(Rutas.MIS_PEDIDOS) {
                            MisPedidosScreen(pedidosClienteVm) { id -> nav.navigate(Rutas.seguimiento(id)) }
                        }

                        composable(Rutas.PERFIL) { Temporal("Perfil (Paso 11)") }

                        // ---------- Admin (Paso 9) ----------
                        composable(Rutas.PANEL) { Temporal("Panel del administrador (Paso 9)") }
                    }
                }
            }
        }
    }
}

@Composable
private fun BarraInferior(items: List<TabItem>, rutaActual: String?, onClick: (String) -> Unit) {
    NavigationBar {
        items.forEach { t ->
            NavigationBarItem(
                selected = rutaActual == t.ruta,
                onClick = { onClick(t.ruta) },
                icon = { Icon(t.icono, contentDescription = t.etiqueta) },
                label = { Text(t.etiqueta) }
            )
        }
    }
}

private fun irATab(nav: NavHostController, ruta: String, inicio: String) {
    nav.navigate(ruta) {
        popUpTo(inicio) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun Temporal(texto: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(texto) }
}