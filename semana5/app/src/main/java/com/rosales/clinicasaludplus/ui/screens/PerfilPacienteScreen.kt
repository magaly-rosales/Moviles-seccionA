package com.rosales.clinicasaludplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosales.clinicasaludplus.ui.theme.GrisTarjetas
import com.rosales.clinicasaludplus.ui.theme.GrisTextoSecundario
import com.rosales.clinicasaludplus.ui.theme.MoradoClaro
import com.rosales.clinicasaludplus.ui.theme.MoradoPrincipal

// Pantalla con el perfil del paciente
@Composable
fun PerfilPacienteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        // Avatar circular con iniciales "JP"
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(MoradoClaro, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MR",
                color = MoradoPrincipal,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        // Nombre del paciente y rol
        Text(
            text = "Magaly Rosales",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Paciente",
            color = GrisTextoSecundario,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(24.dp))

        // Tarjeta con datos personales del paciente
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                FilaDato("Teléfono", "+51 987 654 321")
                Spacer(Modifier.height(12.dp))
                FilaDato("Dirección", "Av. Los Álamos 123, Lima")
                Spacer(Modifier.height(12.dp))
                FilaDato("Seguro", "EsSalud")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Estadísticas del paciente
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TarjetaStat("3", "Citas totales", Modifier.weight(1f))
            TarjetaStat("2", "Médicos vistos", Modifier.weight(1f))
        }
    }
}

// Componente para mostrar cada fila de información
@Composable
fun FilaDato(etiqueta: String, valor: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = etiqueta,
            color = GrisTextoSecundario,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )
        Text(
            text = valor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

// Componente para tarjetas de estadísticas
@Composable
fun TarjetaStat(numero: String, etiqueta: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = numero,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MoradoPrincipal
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = etiqueta,
                color = GrisTextoSecundario,
                fontSize = 12.sp
            )
        }
    }
}
