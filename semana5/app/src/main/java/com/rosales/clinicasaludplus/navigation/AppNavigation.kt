package com.rosales.clinicasaludplus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.model.listaMedicos
import com.rosales.clinicasaludplus.ui.screens.AgendarCitaScreen
import com.rosales.clinicasaludplus.ui.screens.ConfirmacionScreen
import com.rosales.clinicasaludplus.ui.screens.InicioScreen
import com.rosales.clinicasaludplus.ui.screens.PerfilMedicoScreen

@Composable
fun AppNavigation(navController: NavHostController, citas: SnapshotStateList<Cita>) {
    NavHost(navController = navController, startDestination = Screen.Inicio.route) {
        composable(Screen.Inicio.route) {
            InicioScreen(navController = navController)
        }
        composable(
            route = Screen.PerfilMedico.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val medico = listaMedicos.find { it.id == medicoId }
            if (medico != null) {
                PerfilMedicoScreen(navController = navController, medico = medico)
            }
        }
        composable(
            route = Screen.AgendarCita.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val medico = listaMedicos.find { it.id == medicoId }
            if (medico != null) {
                AgendarCitaScreen(navController = navController, medico = medico)
            }
        }
        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            val hora = backStackEntry.arguments?.getString("hora") ?: ""
            val medico = listaMedicos.find { it.id == medicoId }
            if (medico != null) {
                ConfirmacionScreen(
                    navController = navController,
                    medico = medico,
                    fecha = fecha,
                    hora = hora,
                    citas = citas
                )
            }
        }

    }
}