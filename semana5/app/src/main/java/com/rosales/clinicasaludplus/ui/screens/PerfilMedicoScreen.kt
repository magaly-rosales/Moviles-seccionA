package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(navController: NavHostController, medico: Medico) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(medico.nombre, style = MaterialTheme.typography.headlineSmall)
            Text(medico.especialidad, style = MaterialTheme.typography.bodyLarge)
            Text("${medico.calificacion}")

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(Screen.AgendarCita.createRoute(medico.id)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar cita")
            }
        }
    }
}