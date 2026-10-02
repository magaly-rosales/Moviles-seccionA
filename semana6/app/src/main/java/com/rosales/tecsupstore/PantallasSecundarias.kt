package com.rosales.tecsupstore

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PantallaPedidos() {
    val pedidos = listOf(
        Triple("Pedido #1001", "Mouse inalámbrico", "Entregado"),
        Triple("Pedido #1002", "Audífonos Bluetooth", "En camino"),
        Triple("Pedido #1003", "Teclado mecánico", "Preparando")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pedidos) { (codigo, producto, estado) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(codigo, style = MaterialTheme.typography.titleMedium)
                    Text(producto, style = MaterialTheme.typography.bodyMedium)
                    Text(estado, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun PantallaFavoritos(favoritos: SnapshotStateList<Int>) {
    val productosFavoritos = productosEjemplo.filter { it.id in favoritos }

    if (productosFavoritos.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Aún no tienes favoritos", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Márcalos desde el menú ⋮ de cada producto", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productosFavoritos) { producto ->
                TarjetaProducto(producto = producto, favoritos = favoritos)
            }
        }
    }
}

@Composable
fun PantallaPerfil() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MR",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Magaly Rosales", style = MaterialTheme.typography.titleLarge)
        Text("magaly@tecsup.edu.pe", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Diseño y Desarrollo de Software - 4to ciclo", style = MaterialTheme.typography.bodySmall)
    }
}
