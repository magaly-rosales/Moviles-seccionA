package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rosales.clinicasaludplus.model.Cita

@Composable
fun MisCitasScreen(citas: SnapshotStateList<Cita>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis citas", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        if (citas.isEmpty()) {
            Text("Aún no tienes citas agendadas")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(citas) { cita ->
                    val esConfirmada = cita.estado == "Confirmada"
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(cita.medico.nombre, style = MaterialTheme.typography.titleSmall)
                                Text(cita.medico.especialidad)
                                Text("${cita.fecha} · ${cita.hora}")
                            }
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (esConfirmada) Color(0xFFDFF5E1) else Color(0xFFE0E0E0)
                            ) {
                                Text(
                                    cita.estado,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    color = if (esConfirmada) Color(0xFF2E7D32) else Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}