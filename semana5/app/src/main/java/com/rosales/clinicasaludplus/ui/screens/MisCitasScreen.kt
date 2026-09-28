package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.ui.theme.*

// Pantalla "Mis Citas" con la lista de citas agendadas, estado y opción de cancelación
@Composable
fun MisCitasScreen(
    citas: SnapshotStateList<Cita>,
    onShowSnackbar: (String) -> Unit
) {
    // Estado para controlar qué cita se va a cancelar mediante el AlertDialog
    var citaACancelar by remember { mutableStateOf<Cita?>(null) }

    // AlertDialog para confirmar la cancelación de la cita
    if (citaACancelar != null) {
        val cita = citaACancelar!!
        AlertDialog(
            onDismissRequest = { citaACancelar = null },
            title = {
                Text(
                    text = "Cancelar cita",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("¿Seguro que quieres cancelar tu cita con ${cita.medico.nombre}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        citas.remove(cita)
                        citaACancelar = null
                        onShowSnackbar("Cita cancelada")
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
                TextButton(onClick = { citaACancelar = null }) {
                    Text("No")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis citas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(16.dp))

        // Si no hay citas, muestra el mensaje correspondiente
        if (citas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no tienes citas agendadas",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            // Lista vertical de citas
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(citas) { cita ->
                    val esConfirmada = cita.estado == "Confirmada"

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Barra morada decorativa en el borde izquierdo para citas confirmadas
                            if (esConfirmada) {
                                Box(
                                    modifier = Modifier
                                        .width(5.dp)
                                        .height(110.dp)
                                        .background(MoradoPrincipal)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = cita.medico.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    text = "${cita.fecha}, ${cita.hora}",
                                    color = GrisTextoSecundario,
                                    fontSize = 13.sp
                                )

                                Spacer(Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Chip de estado de la cita
                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = if (esConfirmada) VerdeEstadoBg else GrisEstadoBg
                                    ) {
                                        Text(
                                            text = cita.estado,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                            color = if (esConfirmada) VerdeEstadoTexto else GrisEstadoTexto,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    // Botón de Cancelar ÚNICAMENTE para citas "Confirmada"
                                    if (esConfirmada) {
                                        OutlinedButton(
                                            onClick = { citaACancelar = cita },
                                            modifier = Modifier.height(32.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                            shape = RoundedCornerShape(16.dp)
                                        ) {
                                            Text(
                                                text = "Cancelar",
                                                fontSize = 12.sp,
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
    }
}
