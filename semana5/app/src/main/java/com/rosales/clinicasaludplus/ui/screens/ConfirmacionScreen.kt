package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.VerdeEstadoBg
import com.rosales.clinicasaludplus.ui.theme.VerdeEstadoTexto

// Pantalla de confirmación de cita agendada exitosamente
@Composable
fun ConfirmacionScreen(
    navController: NavHostController,
    medico: Medico,
    fecha: String,
    hora: String,
    citas: SnapshotStateList<Cita>
) {
    // Agrega automáticamente la cita a la lista si aún no existe
    LaunchedEffect(Unit) {
        val yaExiste = citas.any {
            it.medico.id == medico.id && it.fecha == fecha && it.hora == hora
        }
        if (!yaExiste) {
            citas.add(0, Cita(medico = medico, fecha = fecha, hora = hora, estado = "Confirmada"))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Círculo verde claro con ícono de check
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(VerdeEstadoBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Éxito",
                tint = VerdeEstadoTexto,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Título "¡Cita agendada!"
        Text(
            text = "¡Cita agendada!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(8.dp))

        // Nombre del médico
        Text(
            text = medico.nombre,
            color = GrisTextoSecundario,
            fontSize = 15.sp
        )

        Spacer(Modifier.height(4.dp))

        // Fecha y Hora
        Text(
            text = "$fecha, $hora",
            color = GrisTextoSecundario,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(40.dp))

        // Botón "Ver mis citas"
        Button(
            onClick = {
                navController.navigate(Screen.MisCitas.route) {
                    popUpTo(Screen.Inicio.route)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEEEEEE),
                contentColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(180.dp)
                .height(44.dp)
        ) {
            Text(
                text = "Ver mis citas",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}
