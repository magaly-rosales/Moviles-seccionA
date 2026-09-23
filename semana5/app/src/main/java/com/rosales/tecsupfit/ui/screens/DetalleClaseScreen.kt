package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Reserva

// Pantalla con el detalle de la clase y validación de reserva
@Composable
fun DetalleClaseScreen(
    clase: ClaseGimnasio,
    reservas: List<Reserva>,
    navController: NavHostController,
    onReservar: () -> Unit
) {
    // Comprobar si el usuario ya reservó esta clase específica
    val yaReservada = reservas.any { it.clase.id == clase.id }
    // Comprobar si no quedan cupos disponibles
    val sinCupos = clase.cuposDisponibles <= 0
    // Solo se permite reservar si no está ya reservada y hay cupos
    val puedeReservar = !yaReservada && !sinCupos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = clase.nombre,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "${clase.horario} · ${clase.sala} · ${clase.duracionMin} min",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        Text(
            text = clase.descripcion,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Botón de reserva desactivado si no se cumple la condición
        Button(
            onClick = onReservar,
            enabled = puedeReservar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reservar cupo")
        }

        // Texto explicativo cuando el botón está deshabilitado
        if (yaReservada) {
            Text(
                text = "Ya tienes una reserva para esta clase.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else if (sinCupos) {
            Text(
                text = "No quedan cupos disponibles para esta clase.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
