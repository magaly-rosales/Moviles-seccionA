package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.listaMedicos
import com.rosales.clinicasaludplus.navigation.Screen
import com.rosales.clinicasaludplus.ui.theme.GrisTarjetas
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.MoradoClaro
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal

// Pantalla principal de inicio con lista de especialidades y médicos disponibles
@Composable
fun InicioScreen(navController: NavHostController) {
    var especialidadSeleccionada by remember { mutableStateOf("Todas") }
    val especialidades = listOf("Todas", "Cardiología", "Pediatría", "Dermatología")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de Filtros por Especialidad (Chips)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(especialidades) { esp ->
                val isSelected = especialidadSeleccionada == esp
                Surface(
                    onClick = { especialidadSeleccionada = esp },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) MoradoPrincipal else GrisTarjetas,
                    modifier = Modifier.height(36.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = esp,
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Título de la lista de médicos
        Text(
            text = "Médicos disponibles",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(12.dp))

        // Filtrado de la lista de médicos según la especialidad seleccionada
        val medicosFiltrados = if (especialidadSeleccionada == "Todas") {
            listaMedicos
        } else {
            listaMedicos.filter { it.especialidad == especialidadSeleccionada }
        }

        // Lista vertical de médicos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(medicosFiltrados) { medico ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.PerfilMedico.createRoute(medico.id))
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Ícono '+' en círculo morado claro según el diseño
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(MoradoClaro, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = MoradoPrincipal,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        // Información del médico: Nombre y Especialidad
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

                        // Calificación con ícono de estrella
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Calificación",
                                tint = Color(0xFFFFB300),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "${medico.calificacion}",
                                fontWeight = FontWeight.Bold,
                                color = GrisTextoSecundario,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
