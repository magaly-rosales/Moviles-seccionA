package com.tecsup.auramakeup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando
import com.tecsup.auramakeup.ui.detalle.DetalleScreen
import com.tecsup.auramakeup.ui.detalle.DetalleViewModel
import com.tecsup.auramakeup.ui.favoritos.FavoritosScreen
import com.tecsup.auramakeup.ui.favoritos.FavoritosViewModel
import com.tecsup.auramakeup.ui.lista.ListaScreen
import com.tecsup.auramakeup.ui.lista.ListaViewModel
import com.tecsup.auramakeup.ui.login.LoginScreen
import com.tecsup.auramakeup.ui.login.LoginViewModel

@Composable
fun AppNav(container: AppContainer) {
    val sesionVm: SesionViewModel = viewModel(factory = SesionViewModel.factory(container))
    val sesion by sesionVm.sesion.collectAsStateWithLifecycle()

    when (val s = sesion) {
        is UiState.Cargando -> Cargando(texto = "Abriendo Aura Makeup...")
        is UiState.Error -> Cargando()
        is UiState.Exito -> {
            val inicio = remember { if (s.datos != null) "lista" else "login" }
            Grafo(
                nav = rememberNavController(),
                container = container,
                sesionVm = sesionVm,
                inicio = inicio
            )
        }
    }
}

@Composable
private fun Grafo(
    nav: NavHostController,
    container: AppContainer,
    sesionVm: SesionViewModel,
    inicio: String
) {
    NavHost(navController = nav, startDestination = inicio) {

        composable("login") {
            val vm: LoginViewModel = viewModel(factory = LoginViewModel.factory(container))
            LoginScreen(
                viewModel = vm,
                onIngresoExitoso = { sesion ->
                    sesionVm.establecer(sesion)
                    nav.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("lista") {
            val vm: ListaViewModel = viewModel(factory = ListaViewModel.factory(container))
            ListaScreen(
                viewModel = vm,
                onItemClick = { id -> nav.navigate("detalle/$id") },
                onFavoritosClick = { nav.navigate("favoritos") },
                onCerrarSesion = {
                    sesionVm.cerrarSesion()
                    nav.navigate("login") {
                        popUpTo(nav.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            val vm: DetalleViewModel = viewModel(factory = DetalleViewModel.factory(container, id))
            DetalleScreen(
                viewModel = vm,
                onVolver = { nav.popBackStack() }
            )
        }

        composable("favoritos") {
            val vm: FavoritosViewModel = viewModel(factory = FavoritosViewModel.factory(container))
            FavoritosScreen(
                viewModel = vm,
                onItemClick = { id -> nav.navigate("detalle/$id") },
                onVolver = { nav.popBackStack() }
            )
        }
    }
}