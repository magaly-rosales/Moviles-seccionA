package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.Periodo
import com.rosales.tecsupfit.model.Reserva
import com.rosales.tecsupfit.theme.GrisTarjetas
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro

// Pantalla de reservas con tarjetas personalizadas (franja lateral y chip de estado)
@Composable
fun ReservasScreen(
    navController: NavHostController,
    reservas: List<Reserva>,
    onCancelarReserva: (Reserva) -> Unit
) {
    // Estado para controlar qué reserva se solicita cancelar
    var reservaACancelar by remember { mutableStateOf<Reserva?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis reservas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (reservas.isEmpty()) {
            Text(
                text = "Aún no tienes reservas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reservas) { reserva ->
                    val esConfirmada = reserva.estado == "Confirmada"

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Franja izquierda (verde oscuro si es Confirmada, gris si es Completada)
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .fillMaxHeight()
                                    .background(if (esConfirmada) VerdeOscuro else Color.Gray)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    // Nombre en negrita
                                    Text(
                                        text = reserva.clase.nombre,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )

                                    // Fecha / Hora
                                    val textoFecha = if (reserva.clase.periodo == Periodo.HOY) "Hoy, " else ""
                                    Text(
                                        text = "$textoFecha${reserva.clase.horario} · ${reserva.clase.sala}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                                    )

                                    // Chip de estado (verde claro si es Confirmada, gris si es Completada)
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (esConfirmada) VerdeClaro else Color(0xFFE0E0E0)
                                    ) {
                                        Text(
                                            text = reserva.estado,
                                            color = if (esConfirmada) VerdeOscuro else Color.DarkGray,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Botón Cancelar disponible solo para reservas en estado "Confirmada"
                                if (esConfirmada) {
                                    OutlinedButton(
                                        onClick = { reservaACancelar = reserva },
                                        border = BorderStroke(1.dp, Color.Gray),
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = "Cancelar",
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación de cancelación con botón "Sí, cancelar" en rojo
    reservaACancelar?.let { reserva ->
        AlertDialog(
            onDismissRequest = { reservaACancelar = null },
            title = { Text("Cancelar reserva", fontWeight = FontWeight.Bold) },
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
                    Text(
                        text = "Sí, cancelar",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { reservaACancelar = null }) {
                    Text("No", color = Color.DarkGray)
                }
            }
        )
    }
}
