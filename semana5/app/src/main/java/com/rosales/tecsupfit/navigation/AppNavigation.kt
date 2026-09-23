package com.rosales.tecsupfit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rosales.tecsupfit.model.listaClases
import com.rosales.tecsupfit.ui.screens.ConfirmacionScreen
import com.rosales.tecsupfit.ui.screens.DetalleClaseScreen
import com.rosales.tecsupfit.ui.screens.InicioScreen
import com.rosales.tecsupfit.ui.screens.PerfilScreen
import com.rosales.tecsupfit.ui.screens.ReservasScreen
import com.rosales.tecsupfit.ui.screens.RutinasScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route,
        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
    ) {
        composable(Screen.Inicio.route) {
            InicioScreen(navController = navController)
        }

        composable(
            route = Screen.Detalle.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 0
            val clase = listaClases.find { it.id == claseId }
            if (clase != null) {
                DetalleClaseScreen(clase = clase, navController = navController)
            }
        }

        composable(Screen.Confirmacion.route) {
            ConfirmacionScreen(navController = navController)
        }

        composable(Screen.Reservas.route) {
            ReservasScreen(navController = navController)
        }

        composable(Screen.Rutinas.route) {
            RutinasScreen(navController = navController)
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(navController = navController)
        }
    }
}