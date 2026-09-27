package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosales.clinicasaludplus.model.listaMedicos

@Composable
fun HistorialMedicoScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historial médico", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text("Médicos que has consultado anteriormente:")
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listaMedicos) { medico ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(medico.nombre, style = MaterialTheme.typography.titleSmall)
                        Text(medico.especialidad)
                    }
                }
            }
        }
    }
}