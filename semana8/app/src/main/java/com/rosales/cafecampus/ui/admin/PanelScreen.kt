package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.PedidoCard
import com.rosales.cafecampus.ui.components.enSoles
import com.rosales.cafecampus.ui.theme.EstadoListo
import com.rosales.cafecampus.ui.theme.EstadoPreparando
import com.rosales.cafecampus.ui.theme.EstadoRecibido
import com.rosales.cafecampus.viewmodel.PanelAdminViewModel

@Composable
fun PanelScreen(vm: PanelAdminViewModel, onVerPedidos: () -> Unit) {
    val nuevos by vm.nuevos.collectAsState()
    val preparando by vm.preparando.collectAsState()
    val listos by vm.listos.collectAsState()
    val ventas by vm.ventasHoy.collectAsState()
    val pedidosNuevos by vm.pedidosNuevos.collectAsState()
    val cs = MaterialTheme.colorScheme

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Panel de control", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador("Nuevos", nuevos, EstadoRecibido, Modifier.weight(1f))
                Contador("Preparando", preparando, EstadoPreparando, Modifier.weight(1f))
                Contador("Listos", listos, EstadoListo, Modifier.weight(1f))
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cs.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Ventas de hoy", color = cs.onPrimaryContainer)
                    Text(
                        ventas.enSoles(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = cs.onPrimaryContainer
                    )
                    Text(
                        "Sin contar pedidos cancelados",
                        style = MaterialTheme.typography.bodySmall,
                        color = cs.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Nuevos pedidos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onVerPedidos) { Text("Ver todos") }
            }
        }
        if (pedidosNuevos.isEmpty()) {
            item { Text("No hay pedidos nuevos", color = cs.onSurfaceVariant) }
        } else {
            items(pedidosNuevos.take(5), key = { it.pedido.id }) { pc ->
                PedidoCard(pc) {
                    Spacer(Modifier.height(8.dp))
                    CafeButton("Preparar", onClick = { vm.preparar(pc) })
                }
            }
        }
    }
}

@Composable
private fun Contador(etiqueta: String, valor: Int, color: Color, modifier: Modifier) {
    Surface(modifier, shape = RoundedCornerShape(12.dp), color = color.copy(alpha = 0.12f)) {
        Column(Modifier.padding(12.dp)) {
            Text("$valor", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = color)
            Text(etiqueta, style = MaterialTheme.typography.labelMedium, color = color)
        }
    }
}