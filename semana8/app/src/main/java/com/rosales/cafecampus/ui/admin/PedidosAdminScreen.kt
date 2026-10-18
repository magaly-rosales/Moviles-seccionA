package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.PedidoCard
import com.rosales.cafecampus.viewmodel.PedidosAdminViewModel

@Composable
fun PedidosAdminScreen(vm: PedidosAdminViewModel, onPedido: (Int) -> Unit) {
    val seleccionado by vm.estadoSeleccionado.collectAsState()
    val pedidos by vm.pedidos.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Text(
            "Pedidos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EstadoPedido.entries.forEach { e ->
                FilterChip(
                    selected = seleccionado == e,
                    onClick = { vm.seleccionar(e) },
                    label = { Text(e.etiqueta) }
                )
            }
        }

        if (pedidos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No hay pedidos en estado ${seleccionado.etiqueta}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(pedidos, key = { it.pedido.id }) { pc ->
                    val siguiente = EstadoPedido.valueOf(pc.pedido.estado).siguiente()
                    PedidoCard(pc, onClick = { onPedido(pc.pedido.id) }) {
                        if (siguiente != null) {
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                CafeButton(
                                    "Pasar a ${siguiente.etiqueta}",
                                    onClick = { vm.avanzar(pc) },
                                    modifier = Modifier.weight(1f)
                                )
                                CafeButton(
                                    "Cancelar",
                                    onClick = { vm.cancelar(pc) },
                                    modifier = Modifier.weight(1f),
                                    secundario = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}