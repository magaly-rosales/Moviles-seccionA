package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rosales.tecsupfit.model.ClaseGimnasio
import com.rosales.tecsupfit.model.Periodo
import com.rosales.tecsupfit.navigation.Screen

// Pantalla de inicio que muestra la lista de clases dinámicas según el período seleccionado
@Composable
fun InicioScreen(
    navController: NavHostController,
    clases: List<ClaseGimnasio>
) {
    // Estado para filtrar las clases por período (HOY o ESTA_SEMANA)
    var periodoSeleccionado by remember { mutableStateOf(Periodo.HOY) }

    // Filtrar las clases dinámicas recibidas desde el estado elevado
    val clasesFiltradas = clases.filter { it.periodo == periodoSeleccionado }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Clases disponibles",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Filtro de períodos
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(Periodo.entries) { periodo ->
                FilterChip(
                    selected = periodo == periodoSeleccionado,
                    onClick = { periodoSeleccionado = periodo },
                    label = {
                        Text(if (periodo == Periodo.HOY) "Hoy" else "Esta semana")
                    }
                )
            }
        }

        // Lista de tarjetas con las clases filtradas
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(clasesFiltradas) { clase ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    onClick = {
                        navController.navigate(Screen.Detalle.crearRuta(clase.id))
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = clase.nombre,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "${clase.horario} · Cupos: ${clase.cuposDisponibles}/${clase.cuposTotales}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
