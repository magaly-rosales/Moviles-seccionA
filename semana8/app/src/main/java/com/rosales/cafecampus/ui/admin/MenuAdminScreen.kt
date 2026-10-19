package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.ui.components.ProductoIcono
import com.rosales.cafecampus.ui.components.enSoles
import com.rosales.cafecampus.ui.theme.EstadoCancelado
import com.rosales.cafecampus.ui.theme.EstadoListo
import com.rosales.cafecampus.viewmodel.MenuAdminViewModel

@Composable
fun MenuAdminScreen(vm: MenuAdminViewModel, onNuevo: () -> Unit, onEditar: (Int) -> Unit) {
    val productos by vm.productos.collectAsState()
    var porEliminar by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevo) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Text(
                "Gestión del menú",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(productos, key = { it.id }) { p ->
                    OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            ProductoIcono(p.icono, Modifier.size(48.dp), imagen = p.imagen)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(p.nombre, fontWeight = FontWeight.Bold)
                                Text(
                                    "${p.categoria} · ${p.precio.enSoles()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.height(4.dp))
                                ChipDisponible(p.disponible) { vm.alternarDisponibilidad(p) }
                            }
                            IconButton(onClick = { onEditar(p.id) }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { porEliminar = p }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }

    // Confirmación antes de eliminar
    porEliminar?.let { p ->
        AlertDialog(
            onDismissRequest = { porEliminar = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Seguro que quieres eliminar \"${p.nombre}\"? Los pedidos antiguos no se verán afectados.") },
            confirmButton = {
                TextButton(onClick = { vm.eliminar(p); porEliminar = null }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { porEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun ChipDisponible(disponible: Boolean, onClick: () -> Unit) {
    val c = if (disponible) EstadoListo else EstadoCancelado
    Surface(onClick = onClick, color = c.copy(alpha = 0.15f), shape = RoundedCornerShape(50)) {
        Text(
            if (disponible) "Disponible" else "Agotado",
            color = c,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}