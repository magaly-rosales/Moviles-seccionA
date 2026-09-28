package com.rosales.clinicasaludplus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.model.listaMedicos
import com.rosales.clinicasaludplus.navigation.AppNavigation
import com.rosales.clinicasaludplus.navigation.Screen
import com.rosales.clinicasaludplus.ui.theme.MoradoClaro
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicaSaludApp() {
    val navController = rememberNavController()

    // Estado global de la lista de citas (gestionado con mutableStateListOf para reactividad sin ViewModel)
    // Se agregan ejemplos iniciales, incluyendo 2 con estado "Completada" como requiere la especificación
    val citas = remember {
        mutableStateListOf(
            Cita(
                medico = listaMedicos[0], // Dra. Ana Torres
                fecha = "Viernes 27",
                hora = "10:30 am",
                estado = "Confirmada"
            ),
            Cita(
                medico = listaMedicos[1], // Dr. Luis Vega
                fecha = "Miércoles 15",
                hora = "3:00 pm",
                estado = "Completada"
            ),
            Cita(
                medico = listaMedicos[2], // Dra. Rosa Díaz
                fecha = "Lunes 10",
                hora = "9:00 am",
                estado = "Completada"
            )
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Función auxiliar para mostrar mensajes Snackbar desde cualquier pantalla
    val onShowSnackbar: (String) -> Unit = { mensaje ->
        scope.launch {
            snackbarHostState.showSnackbar(mensaje)
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Menú lateral (ModalNavigationDrawer) personalizado según el diseño de la Figura 1
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Encabezado del Drawer con avatar circular morado claro e iniciales
                Column(modifier = Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(MoradoClaro, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MR",
                            color = MoradoPrincipal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Magaly Rosales",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Paciente",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(Modifier.height(8.dp))

                // Elementos del menú del Drawer con ícono tipo radio button
                val menuItems = listOf(
                    Triple("Inicio", Screen.Inicio.route, Screen.Inicio.route),
                    Triple("Mis citas", Screen.MisCitas.route, Screen.MisCitas.route),
                    Triple("Historial médico", Screen.HistorialMedico.route, Screen.HistorialMedico.route),
                    Triple("Perfil", Screen.PerfilPaciente.route, Screen.PerfilPaciente.route)
                )

                menuItems.forEach { (titulo, ruta, targetRoute) ->
                    val isSelected = currentRoute == ruta
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = titulo,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (isSelected) MoradoPrincipal else Color.Gray
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(targetRoute) {
                                if (targetRoute == Screen.Inicio.route) {
                                    popUpTo(Screen.Inicio.route) { inclusive = true }
                                }
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MoradoClaro,
                            selectedTextColor = MoradoPrincipal,
                            selectedIconColor = MoradoPrincipal,
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = Color.Black,
                            unselectedIconColor = Color.Gray
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                // TopAppBar morada para la pantalla de Inicio
                if (currentRoute == Screen.Inicio.route) {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Clínica Salud+",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Hola, Magaly",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menú",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MoradoPrincipal
                        )
                    )
                } else if (currentRoute == Screen.MisCitas.route ||
                    currentRoute == Screen.HistorialMedico.route ||
                    currentRoute == Screen.PerfilPaciente.route
                ) {
                    // TopAppBar estándar para las demás pantallas principales
                    TopAppBar(
                        title = {
                            val titulo = when (currentRoute) {
                                Screen.MisCitas.route -> "Mis citas"
                                Screen.HistorialMedico.route -> "Historial médico"
                                else -> "Perfil"
                            }
                            Text(titulo, fontWeight = FontWeight.Bold)
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menú"
                                )
                            }
                        }
                    )
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavigation(
                    navController = navController,
                    citas = citas,
                    onShowSnackbar = onShowSnackbar
                )
            }
        }
    }
}
