package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(navController: NavHostController, medico: Medico) {
    val fechas = listOf("Lun 29 Sep", "Mar 30 Sep", "Mié 01 Oct")
    val horas = listOf("9:00 am", "11:00 am", "3:00 pm")

    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
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
                .padding(16.dp)
        ) {
            Text(medico.nombre, style = MaterialTheme.typography.titleLarge)
            Text(medico.especialidad)

            Spacer(Modifier.height(24.dp))
            Text("Elige una fecha", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                fechas.forEach { fecha ->
                    FilterChip(
                        selected = fechaSeleccionada == fecha,
                        onClick = { fechaSeleccionada = fecha },
                        label = { Text(fecha) }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Elige una hora", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                horas.forEach { hora ->
                    FilterChip(
                        selected = horaSeleccionada == hora,
                        onClick = { horaSeleccionada = hora },
                        label = { Text(hora) }
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Confirmacion.createRoute(
                            medicoId = medico.id,
                            fecha = fechaSeleccionada ?: "",
                            hora = horaSeleccionada ?: ""
                        )
                    )
                },
                enabled = fechaSeleccionada != null && horaSeleccionada != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar cita")
            }
        }
    }
}