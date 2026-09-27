package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.theme.GrisTarjetas
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro

// Modelo de datos para las rutinas
data class Rutina(
    val id: Int,
    val nombre: String,
    val duracion: String,
    val nivel: String,
    val descripcion: String
)

// Catálogo de rutinas de ejemplo
val listaRutinas = listOf(
    Rutina(
        id = 1,
        nombre = "Full body",
        duracion = "45 min",
        nivel = "Principiante",
        descripcion = "Rutina completa para tonificar todos los grupos musculares."
    ),
    Rutina(
        id = 2,
        nombre = "Cardio HIIT",
        duracion = "30 min",
        nivel = "Intermedio",
        descripcion = "Ejercicios de alta intensidad con intervalos para quema de grasa."
    ),
    Rutina(
        id = 3,
        nombre = "Fuerza & Piernas",
        duracion = "50 min",
        nivel = "Avanzado",
        descripcion = "Trabajo enfocado en tren inferior con pesas y potencia."
    ),
    Rutina(
        id = 4,
        nombre = "Core & Abdominales",
        duracion = "20 min",
        nivel = "Principiante",
        descripcion = "Fortalecimiento de zona media, estabilidad y postura."
    ),
    Rutina(
        id = 5,
        nombre = "Yoga & Flexibilidad",
        duracion = "40 min",
        nivel = "Todos los niveles",
        descripcion = "Sesión de movilidad articular y estiramientos guiados."
    )
)

// Pantalla con tarjetas estilizadas en gris y recuadro verde claro con ícono de pesa
@Composable
fun RutinasScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Rutinas de entrenamiento",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaRutinas) { rutina ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Recuadro verde claro con ícono de pesa
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(VerdeClaro),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = "Rutina",
                                tint = VerdeOscuro,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = rutina.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${rutina.duracion} · ${rutina.nivel}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Text(
                                text = rutina.descripcion,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
