package com.rosales.tecsupfit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rosales.tecsupfit.model.Reserva
import com.rosales.tecsupfit.model.listaClases
import com.rosales.tecsupfit.model.listaReservas
import com.rosales.tecsupfit.navigation.AppNavigation
import com.rosales.tecsupfit.navigation.Screen
import com.rosales.tecsupfit.theme.FondoBlanco
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecsupFitApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Host state y CoroutineScope para Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado global elevado de clases y reservas
    val clases = remember { mutableStateListOf(*listaClases.toTypedArray()) }
    val reservas = remember { mutableStateListOf(*listaReservas.toTypedArray()) }

    // Función para procesar reserva de cupo
    fun reservarClase(claseId: Int) {
        val index = clases.indexOfFirst { it.id == claseId }
        if (index != -1 && clases[index].cuposDisponibles > 0) {
            val claseActualizada = clases[index].copy(
                cuposDisponibles = clases[index].cuposDisponibles - 1
            )
            clases[index] = claseActualizada
            reservas.add(0, Reserva(clase = claseActualizada, estado = "Confirmada"))
        }
    }

    // Función para cancelar reserva
    fun cancelarReserva(reserva: Reserva) {
        reservas.remove(reserva)
        val index = clases.indexOfFirst { it.id == reserva.clase.id }
        if (index != -1) {
            clases[index] = clases[index].copy(
                cuposDisponibles = clases[index].cuposDisponibles + 1
            )
        }
        scope.launch {
            snackbarHostState.showSnackbar("Reserva cancelada")
        }
    }

    val itemsBottomBar = listOf(
        Triple(Screen.Inicio.route, "Inicio", Icons.Filled.Home),
        Triple(Screen.Reservas.route, "Reservas", Icons.Filled.DateRange),
        Triple(Screen.Rutinas.route, "Rutinas", Icons.Filled.FitnessCenter),
        Triple(Screen.Perfil.route, "Perfil", Icons.Filled.Person)
    )

    val esInicio = currentRoute == Screen.Inicio.route || currentRoute == null
    val esDetalle = currentRoute?.startsWith("detalle") == true
    val esConfirmacion = currentRoute?.startsWith("confirmacion") == true
    val muestraTopBar = !esConfirmacion
    val muestraBottomBar = !esDetalle && !esConfirmacion

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FondoBlanco,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (muestraTopBar) {
                TopAppBar(
                    title = {
                        if (esInicio) {
                            Column {
                                Text(
                                    text = "TECSUP Fit",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Hola, Magaly",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        } else {
                            val titulo = when {
                                esDetalle -> "Detalle de clase"
                                currentRoute == Screen.Reservas.route -> "Mis reservas"
                                currentRoute == Screen.Rutinas.route -> "Rutinas"
                                currentRoute == Screen.Perfil.route -> "Perfil"
                                else -> "TECSUP Fit"
                            }
                            Text(
                                text = titulo,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    },
                    navigationIcon = {
                        if (esDetalle) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = Color.Black
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (esInicio) VerdeOscuro else FondoBlanco
                    )
                )
            }
        },
        bottomBar = {
            if (muestraBottomBar) {
                NavigationBar(
                    containerColor = FondoBlanco
                ) {
                    itemsBottomBar.forEach { (route, label, icon) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {
                                if (currentRoute != route) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = route != Screen.Inicio.route
                                    }
                                }
                            },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = VerdeOscuro,
                                selectedTextColor = VerdeOscuro,
                                indicatorColor = VerdeClaro,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            paddingValues = innerPadding,
            clases = clases,
            reservas = reservas,
            onReservarClase = { claseId -> reservarClase(claseId) },
            onCancelarReserva = { reserva -> cancelarReserva(reserva) }
        )
    }
}
