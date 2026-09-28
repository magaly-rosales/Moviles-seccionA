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
import com.rosales.clinicasaludplus.ui.screens.*

// Grafo de navegación principal de la aplicación
@Composable
fun AppNavigation(
    navController: NavHostController,
    citas: SnapshotStateList<Cita>,
    onShowSnackbar: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Inicio.route) {
        // Pantalla de Inicio
        composable(Screen.Inicio.route) {
            InicioScreen(navController = navController)
        }

        // Pantalla de Perfil del Médico
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

        // Pantalla para Agendar Cita
        composable(
            route = Screen.AgendarCita.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val medico = listaMedicos.find { it.id == medicoId }
            if (medico != null) {
                AgendarCitaScreen(
                    navController = navController,
                    medico = medico,
                    citas = citas,
                    onShowSnackbar = onShowSnackbar
                )
            }
        }

        // Pantalla de Confirmación de Cita
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

        // Pantalla Mis Citas
        composable(Screen.MisCitas.route) {
            MisCitasScreen(
                citas = citas,
                onShowSnackbar = onShowSnackbar
            )
        }

        // Pantalla Historial Médico
        composable(Screen.HistorialMedico.route) {
            HistorialMedicoScreen()
        }

        // Pantalla Perfil del Paciente
        composable(Screen.PerfilPaciente.route) {
            PerfilPacienteScreen()
        }
    }
}
