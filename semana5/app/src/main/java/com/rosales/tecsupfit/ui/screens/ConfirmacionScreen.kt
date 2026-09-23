package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Periodo
import com.rosales.tecsupfit.navigation.Screen
import com.rosales.tecsupfit.theme.GrisTarjetas
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro

// Pantalla de confirmación con diseño centrado y botón secundario gris
@Composable
fun ConfirmacionScreen(
    clase: ClaseGimnasio,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Círculo verde claro con ícono check verde oscuro
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(VerdeClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirmado",
                    tint = VerdeOscuro,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Título principal en negrita
            Text(
                text = "¡Cupo reservado!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Nombre de la clase
            Text(
                text = clase.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            // Subtítulo con horario y sala
            val textoPeriodo = if (clase.periodo == Periodo.HOY) "Hoy, " else ""
            Text(
                text = "$textoPeriodo${clase.horario} · ${clase.sala}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
            )

            // Botón "Ver mis reservas" en gris claro con texto oscuro
            Button(
                onClick = {
                    navController.navigate(Screen.Reservas.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GrisTarjetas,
                    contentColor = Color.DarkGray
                )
            ) {
                Text(
                    text = "Ver mis reservas",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
