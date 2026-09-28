package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rosales.clinicasaludplus.model.Medico
import com.rosales.clinicasaludplus.navigation.Screen
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.MoradoClaro
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal

// Pantalla con el detalle y perfil completo del médico
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(navController: NavHostController, medico: Medico) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Ícono grande '+' en círculo morado claro centrado arriba
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(MoradoClaro, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MoradoPrincipal,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Nombre del médico en negrita
            Text(
                text = medico.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(Modifier.height(4.dp))

            // Especialidad y experiencia
            Text(
                text = "${medico.especialidad} · ${medico.experiencia}",
                color = GrisTextoSecundario,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(8.dp))

            // Calificación con estrella y número de reseñas
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${medico.calificacion} (${medico.resenasCount} reseñas)",
                    color = GrisTextoSecundario,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            // Descripción detallada del médico
            Text(
                text = medico.descripcion,
                color = Color.DarkGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.weight(1f))

            // Botón "Agendar cita" morado fijo en la parte inferior
            Button(
                onClick = { navController.navigate(Screen.AgendarCita.createRoute(medico.id)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrincipal,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Agendar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
