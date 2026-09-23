package com.rosales.tecsupfit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Reserva
import com.rosales.tecsupfit.ui.screens.ConfirmacionScreen
import com.rosales.tecsupfit.ui.screens.DetalleClaseScreen
import com.rosales.tecsupfit.ui.screens.InicioScreen
import com.rosales.tecsupfit.ui.screens.PerfilScreen
import com.rosales.tecsupfit.ui.screens.ReservasScreen
import com.rosales.tecsupfit.ui.screens.RutinasScreen

// Control de navegación principal con paso de estados y funciones por parámetros
@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    clases: List<ClaseGimnasio>,
    reservas: List<Reserva>,
    onReservarClase: (Int) -> Unit,
    onCancelarReserva: (Reserva) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route,
        modifier = androidx.compose.ui.Modifier.padding(paddingValues)
    ) {
        // Pantalla de Inicio
        composable(Screen.Inicio.route) {
            InicioScreen(
                navController = navController,
                clases = clases
            )
        }

        // Pantalla de Detalle de Clase
        composable(
            route = Screen.Detalle.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 0
            val clase = clases.find { it.id == claseId }
            if (clase != null) {
                DetalleClaseScreen(
                    clase = clase,
                    reservas = reservas,
                    navController = navController,
                    onReservar = {
                        onReservarClase(clase.id)
                        navController.navigate(Screen.Confirmacion.crearRuta(clase.id))
                    }
                )
            }
        }

        // Pantalla de Confirmación
        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 0
            val clase = clases.find { it.id == claseId }
            if (clase != null) {
                ConfirmacionScreen(clase = clase, navController = navController)
            }
        }

        // Pantalla de Reservas del usuario
        composable(Screen.Reservas.route) {
            ReservasScreen(
                navController = navController,
                reservas = reservas,
                onCancelarReserva = onCancelarReserva
            )
        }

        // Pantalla de Rutinas
        composable(Screen.Rutinas.route) {
            RutinasScreen(navController = navController)
        }

        // Pantalla de Perfil
        composable(Screen.Perfil.route) {
            PerfilScreen(navController = navController)
        }
    }
}
