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
import com.rosales.tecsupfit.model.Reserva

// Pantalla que muestra el listado dinámico de reservas del usuario
@Composable
fun ReservasScreen(
    navController: NavHostController,
    reservas: List<Reserva>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis reservas",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Si no existen reservas en la lista, muestra un mensaje explicativo
        if (reservas.isEmpty()) {
            Text(
                text = "Aún no tienes reservas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            // Renderiza la lista dinámica de reservas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reservas) { reserva ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = reserva.clase.nombre,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = reserva.clase.horario,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = reserva.estado,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (reserva.estado == "Confirmada")
                                    Color(0xFF2E7D32)
                                else
                                    Color.Gray,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
