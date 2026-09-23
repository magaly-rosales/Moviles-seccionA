package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Periodo
import com.rosales.tecsupfit.navigation.Screen
import com.rosales.tecsupfit.theme.GrisTarjetas
import com.rosales.tecsupfit.theme.VerdeClaro
import com.rosales.tecsupfit.theme.VerdeOscuro

// Pantalla principal con catálogo de clases y filtros de período
@Composable
fun InicioScreen(
    navController: NavHostController,
    clases: List<ClaseGimnasio>
) {
    // Estado para filtrar las clases por el período seleccionado
    var periodoSeleccionado by remember { mutableStateOf(Periodo.HOY) }

    // Filtrar la lista de clases según el período activo
    val clasesFiltradas = clases.filter { it.periodo == periodoSeleccionado }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chips de selección de período ("Hoy" / "Esta semana")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(Periodo.entries) { periodo ->
                val esSeleccionado = periodo == periodoSeleccionado
                FilterChip(
                    selected = esSeleccionado,
                    onClick = { periodoSeleccionado = periodo },
                    label = {
                        Text(
                            text = if (periodo == Periodo.HOY) "Hoy" else "Esta semana",
                            fontWeight = if (esSeleccionado) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdeOscuro,
                        selectedLabelColor = Color.White,
                        containerColor = GrisTarjetas,
                        labelColor = Color.DarkGray
                    ),
                    border = null
                )
            }
        }

        // Título de la sección
        Text(
            text = "Clases disponibles",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Listado de tarjetas de clases con recuadro verde claro a la izquierda
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(clasesFiltradas) { clase ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisTarjetas),
                    onClick = {
                        navController.navigate(Screen.Detalle.crearRuta(clase.id))
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Recuadro verde claro con ícono de pesa
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(VerdeClaro),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = "Clase",
                                tint = VerdeOscuro,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = clase.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${clase.horario} · ${clase.sala}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
