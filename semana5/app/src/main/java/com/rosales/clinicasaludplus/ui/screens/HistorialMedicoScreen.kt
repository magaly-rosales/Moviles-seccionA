package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosales.clinicasaludplus.model.listaMedicos
import com.rosales.clinicasaludplus.ui.theme.GrisTarjetas
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.MoradoClaro
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal

// Pantalla con el historial médico del paciente
@Composable
fun HistorialMedicoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historial médico",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Médicos que has consultado anteriormente:",
            color = GrisTextoSecundario,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(16.dp))

        // Lista de médicos vistos previamente
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(listaMedicos) { medico ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar iniciales
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(MoradoClaro, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = medico.nombre.take(2).uppercase(),
                                color = MoradoPrincipal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = medico.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = medico.especialidad,
                                color = GrisTextoSecundario,
                                fontSize = 13.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFB300),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "${medico.calificacion}",
                                fontWeight = FontWeight.Bold,
                                color = GrisTextoSecundario,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
