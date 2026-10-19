package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.PedidoCard
import com.rosales.cafecampus.viewmodel.PedidosClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisPedidosScreen(vm: PedidosClienteViewModel, onPedido: (Int) -> Unit) {
    val activos by vm.activos.collectAsState()
    val historial by vm.historial.collectAsState()
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val lista = if (tab == 0) activos else historial

    Column(Modifier.fillMaxSize()) {
        Text(
            "Mis pedidos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        PrimaryTabRow(selectedTabIndex = tab, containerColor = MaterialTheme.colorScheme.background) {
            Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Activos (${activos.size})") })
            Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Historial") })
        }
        if (lista.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    if (tab == 0) "No tienes pedidos activos" else "Aún no hay pedidos en tu historial",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(lista, key = { it.pedido.id }) { pc ->
                    PedidoCard(pc, onClick = { onPedido(pc.pedido.id) })
                }
            }
        }
    }
}