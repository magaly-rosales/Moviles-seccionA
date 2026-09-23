package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.theme.GrisTarjetas
import com.rosales.tecsupfit.theme.VerdeOscuro

@Composable
fun PerfilScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        // Avatar con iniciales
        Box(
            modifier = Modifier.size(90.dp).background(VerdeOscuro, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("MR", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        Text("Magaly Rosales", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Plan Premium", color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        // Dos tarjetas de estadísticas
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TarjetaStat("14", "Clases", Modifier.weight(1f))
            TarjetaStat("3", "Racha", Modifier.weight(1f))
        }
    }
}

@Composable
fun TarjetaStat(numero: String, etiqueta: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisTarjetas)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(numero, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = VerdeOscuro)
            Text(etiqueta, color = Color.Gray, fontSize = 13.sp)
        }
    }
}