package com.rosales.tecsupfit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rosales.tecsupfit.navigation.AppNavigation
import com.rosales.tecsupfit.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecsupFitApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val itemsBottomBar = listOf(
        Triple(Screen.Inicio.route, "Inicio", Icons.Filled.Home),
        Triple(Screen.Reservas.route, "Reservas", Icons.Filled.DateRange),
        Triple(Screen.Rutinas.route, "Rutinas", Icons.Filled.FitnessCenter),
        Triple(Screen.Perfil.route, "Perfil", Icons.Filled.Person)
    )

    val esDetalle = currentRoute?.startsWith("detalle") == true
    val esConfirmacion = currentRoute?.startsWith("confirmacion") == true
    val muestraBackButton = esDetalle || esConfirmacion

    val tituloPantalla = when {
        esDetalle -> "Detalle de clase"
        esConfirmacion -> "Confirmación"
        currentRoute == Screen.Reservas.route -> "Mis reservas"
        currentRoute == Screen.Rutinas.route -> "Rutinas"
        currentRoute == Screen.Perfil.route -> "Perfil"
        else -> "TecsupFit"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(tituloPantalla) },
                navigationIcon = {
                    if (muestraBackButton) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                itemsBottomBar.forEach { (route, label, icon) ->
                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            paddingValues = innerPadding
        )
    }
}