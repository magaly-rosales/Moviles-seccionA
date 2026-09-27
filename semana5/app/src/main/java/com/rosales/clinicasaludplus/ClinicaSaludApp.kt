package com.rosales.clinicasaludplus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.navigation.AppNavigation
import com.rosales.clinicasaludplus.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicaSaludApp() {
    val navController = rememberNavController()
    val citas = remember { mutableStateListOf<Cita>() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Clínica Salud+",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Paciente: Magaly Rosales",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == Screen.Inicio.route,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Inicio.route) {
                            popUpTo(Screen.Inicio.route) { inclusive = true }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Mis citas") },
                    selected = currentRoute == Screen.MisCitas.route,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.MisCitas.route)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Historial médico") },
                    selected = currentRoute == Screen.HistorialMedico.route,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.HistorialMedico.route)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = currentRoute == Screen.PerfilPaciente.route,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.PerfilPaciente.route)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Clínica Salud+") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavigation(navController = navController, citas = citas)
            }
        }
    }
}