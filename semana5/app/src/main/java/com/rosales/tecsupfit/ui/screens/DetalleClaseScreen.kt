package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Reserva
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro

// Pantalla con el detalle de la clase y el botón de reserva fijo en la parte inferior
@Composable
fun DetalleClaseScreen(
    clase: ClaseGimnasio,
    reservas: List<Reserva>,
    navController: NavHostController,
    onReservar: () -> Unit
) {
    // Validaciones de reserva
    val yaReservada = reservas.any { it.clase.id == clase.id }
    val sinCupos = clase.cuposDisponibles <= 0
    val puedeReservar = !yaReservada && !sinCupos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Contenido desplazable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Recuadro grande verde claro con ícono de pesa centrado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = VerdeOscuro,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre en negrita
            Text(
                text = clase.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Subtítulo: horario · Sala · duración
            Text(
                text = "${clase.horario} · ${clase.sala} · ${clase.duracionMin} min",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Descripción
            Text(
                text = clase.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Cupos disponibles
            Text(
                text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Botón fijo abajo (fuera del scroll)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = onReservar,
                enabled = puedeReservar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeOscuro,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Text(
                    text = "Reservar cupo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mensajes de error/explicativos si no se puede reservar
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
}
