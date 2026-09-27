package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Cita
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen

@Composable
fun ConfirmacionScreen(
    navController: NavHostController,
    medico: Medico,
    fecha: String,
    hora: String,
    citas: SnapshotStateList<Cita>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Cita agendada!", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text(medico.nombre, style = MaterialTheme.typography.titleMedium)
        Text(medico.especialidad)
        Text("$fecha · $hora")

        Spacer(Modifier.height(32.dp))

        Button(onClick = {
            val yaExiste = citas.any {
                it.medico.id == medico.id && it.fecha == fecha && it.hora == hora
            }
            if (!yaExiste) {
                citas.add(0, Cita(medico = medico, fecha = fecha, hora = hora))
            }
            navController.navigate(Screen.MisCitas.route) {
                popUpTo(Screen.Inicio.route)
            }
        }) {
            Text("Ver mis citas")
        }
    }
}