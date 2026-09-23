package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.Reserva

// Pantalla que muestra la lista de reservas del usuario con opción de cancelación
@Composable
fun ReservasScreen(
    navController: NavHostController,
    reservas: List<Reserva>,
    onCancelarReserva: (Reserva) -> Unit
) {
    // Estado para controlar qué reserva se desea cancelar y mostrar el diálogo de confirmación
    var reservaACancelar by remember { mutableStateOf<Reserva?>(null) }

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

        // Si la lista está vacía, se informa al usuario
        if (reservas.isEmpty()) {
            Text(
                text = "Aún no tienes reservas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            // Renderizado dinámico de las reservas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reservas) { reserva ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
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

                            // Únicamente las reservas en estado "Confirmada" pueden cancelarse
                            if (reserva.estado == "Confirmada") {
                                OutlinedButton(
                                    onClick = { reservaACancelar = reserva },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para cancelar reserva
    reservaACancelar?.let { reserva ->
        AlertDialog(
            onDismissRequest = { reservaACancelar = null },
            title = { Text("Cancelar reserva") },
            text = {
                Text("¿Seguro que quieres cancelar tu reserva de ${reserva.clase.nombre}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCancelarReserva(reserva)
                        reservaACancelar = null
                    }
                ) {
                    Text("Sí, cancelar")
                }
            },
            dismissButton = {
                TextButton(onClick = { reservaACancelar = null }) {
                    Text("No")
                }
            }
        )
    }
}
