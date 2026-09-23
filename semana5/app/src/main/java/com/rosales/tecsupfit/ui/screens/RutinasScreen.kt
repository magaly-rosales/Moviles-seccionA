package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Estructura de datos para las rutinas de ejercicio
data class Rutina(
    val id: Int,
    val nombre: String,
    val duracion: String,
    val nivel: String,
    val descripcion: String
)

// Listado de rutinas de ejemplo
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

// Pantalla que muestra el listado de rutinas disponibles
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
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Renderizado de las tarjetas de rutinas
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaRutinas) { rutina ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = rutina.nombre,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "${rutina.duracion} · ${rutina.nivel}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = rutina.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
