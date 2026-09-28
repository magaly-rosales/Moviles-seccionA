package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen
import com.rosales.clinicasaludplus.ui.theme.GrisTarjetas
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal

// Estructura de datos para representar la fecha en formato de día y número
data class FechaOption(val diaSemana: String, val numero: String, val textoCompleto: String)

// Pantalla para agendar una nueva cita con selección de fecha y hora
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    navController: NavHostController,
    medico: Medico,
    citas: SnapshotStateList<Cita>,
    onShowSnackbar: (String) -> Unit
) {
    // Lista de opciones de fechas con día y número según la Figura 1
    val opcionesFechas = listOf(
        FechaOption("Jue", "26", "Jueves 26"),
        FechaOption("Vie", "27", "Viernes 27"),
        FechaOption("Sáb", "28", "Sábado 28")
    )

    // Lista de opciones de horas
    val opcionesHoras = listOf("9:00", "10:30", "3:00")

    var fechaSeleccionada by remember { mutableStateOf<FechaOption?>(opcionesFechas[1]) } // Vie 27 por defecto
    var horaSeleccionada by remember { mutableStateOf<String?>("10:30") } // 10:30 por defecto

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            // Nombre y especialidad del médico seleccionado
            Text(
                text = "Selecciona fecha",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(12.dp))

            // Chips de Fecha en disposición horizontal
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                opcionesFechas.forEach { option ->
                    val isSelected = fechaSeleccionada == option
                    Surface(
                        modifier = Modifier
                            .width(70.dp)
                            .clickable { fechaSeleccionada = option },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MoradoPrincipal else GrisTarjetas
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = option.diaSemana,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White.copy(alpha = 0.8f) else GrisTextoSecundario
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = option.numero,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Selecciona hora",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(12.dp))

            // Chips de Hora en disposición horizontal
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                opcionesHoras.forEach { hora ->
                    val isSelected = horaSeleccionada == hora
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { horaSeleccionada = hora },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MoradoPrincipal else GrisTarjetas
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = hora,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // Botón "Confirmar cita" morado
            Button(
                onClick = {
                    val fechaTexto = fechaSeleccionada?.textoCompleto ?: ""
                    val horaTexto = if (horaSeleccionada?.contains("m") == true) horaSeleccionada!! else "${horaSeleccionada} am"

                    // Validación: comprobar si la misma cita ya existe en la lista
                    val yaExiste = citas.any {
                        it.medico.id == medico.id &&
                                (it.fecha == fechaTexto || it.fecha.contains(fechaSeleccionada?.numero ?: "")) &&
                                it.hora.startsWith(horaSeleccionada ?: "")
                    }

                    if (yaExiste) {
                        // Muestra aviso de aviso si ya está agendada
                        onShowSnackbar("Ya tienes una cita agendada para esa fecha y hora.")
                    } else {
                        // Navega a la pantalla de confirmación si no hay duplicados
                        navController.navigate(
                            Screen.Confirmacion.createRoute(
                                medicoId = medico.id,
                                fecha = fechaTexto,
                                hora = horaTexto
                            )
                        )
                    }
                },
                enabled = fechaSeleccionada != null && horaSeleccionada != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrincipal,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Confirmar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
