package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.listaMedicos

@Composable
fun InicioScreen(navController: NavHostController) {
    var especialidadSeleccionada by remember { mutableStateOf("Todas") }
    val especialidades = listOf("Todas", "Cardiología", "Pediatría", "Dermatología")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Especialidades", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(especialidades) { esp ->
                FilterChip(
                    selected = especialidadSeleccionada == esp,
                    onClick = { especialidadSeleccionada = esp },
                    label = { Text(esp) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Médicos disponibles", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        val medicosFiltrados = if (especialidadSeleccionada == "Todas") {
            listaMedicos
        } else {
            listaMedicos.filter { it.especialidad == especialidadSeleccionada }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(medicosFiltrados) { medico ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(medico.nombre, style = MaterialTheme.typography.titleSmall)
                        Text(medico.especialidad)
                        Text("${medico.calificacion}")
                    }
                }
            }
        }
    }
}