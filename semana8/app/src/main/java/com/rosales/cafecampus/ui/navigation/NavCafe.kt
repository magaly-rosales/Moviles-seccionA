package com.rosales.cafecampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rosales.cafecampus.CafeApp
import com.rosales.cafecampus.ui.cliente.MenuScreen
import com.rosales.cafecampus.ui.cliente.SplashScreen
import com.rosales.cafecampus.viewmodel.CafeViewModelFactory
import com.rosales.cafecampus.viewmodel.MenuViewModel

@Composable
fun NavCafe(app: CafeApp) {
    val nav = rememberNavController()
    val factory = remember { CafeViewModelFactory(app.repository) }
    val menuVm: MenuViewModel = viewModel(factory = factory)

    NavHost(navController = nav, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) {
            SplashScreen {
                nav.navigate(Rutas.MENU) { popUpTo(Rutas.SPLASH) { inclusive = true } }
            }
        }
        composable(Rutas.MENU) {
            MenuScreen(menuVm, onProducto = {})
        }

    }
}