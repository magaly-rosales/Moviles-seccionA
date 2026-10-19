package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.ui.components.AppTopBar
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.EstadoChip
import com.rosales.cafecampus.ui.components.enSoles
import com.rosales.cafecampus.ui.theme.EstadoPreparando
import com.rosales.cafecampus.viewmodel.PedidosAdminViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetallePedidoAdminScreen(vm: PedidosAdminViewModel, pedidoId: Int, onAtras: () -> Unit) {
    val pc by remember(pedidoId) { vm.pedido(pedidoId) }.collectAsState()

    Scaffold(
        topBar = { AppTopBar("Pedido #$pedidoId", onAtras = onAtras) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val p = pc
        if (p == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val estado = EstadoPedido.valueOf(p.pedido.estado)
            val siguiente = estado.siguiente()
            val cs = MaterialTheme.colorScheme

            Column(
                Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState()).padding(16.dp)
            ) {
                // Cliente
                OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                p.cliente?.nombre ?: "Cliente",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            EstadoChip(estado)
                        }
                        p.cliente?.let {
                            Text(it.correo, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Recojo: ${p.pedido.horaRecojo} · Pago: ${if (p.pedido.pago == "YAPE") "Yape / Plin" else "Caja"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Líneas
                Spacer(Modifier.height(16.dp))
                Text("Productos", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                p.detalles.forEach { d ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text("${d.cantidad}x ${d.nombreProducto}", modifier = Modifier.weight(1f))
                        Text((d.precioUnitario * d.cantidad).enSoles())
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text("Total", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(p.pedido.total.enSoles(), fontWeight = FontWeight.Bold, color = cs.primary)
                }

                // Nota del cliente (tarjeta ámbar)
                if (p.pedido.nota.isNotBlank()) {
                    Spacer(Modifier.height(16.dp))
                    Surface(
                        color = EstadoPreparando.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Nota del cliente", fontWeight = FontWeight.Bold, color = EstadoPreparando)
                            Text(p.pedido.nota)
                        }
                    }
                }

                // Selector de estado
                Spacer(Modifier.height(16.dp))
                Text("Estado", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    EstadoPedido.entries.forEach { e ->
                        FilterChip(
                            selected = estado == e,
                            onClick = { vm.cambiarA(p, e) },
                            label = { Text(e.etiqueta) }
                        )
                    }
                }

                // Acciones
                if (siguiente != null) {
                    Spacer(Modifier.height(20.dp))
                    CafeButton("Pasar a ${siguiente.etiqueta}", onClick = { vm.avanzar(p) })
                    Spacer(Modifier.height(8.dp))
                    CafeButton("Cancelar pedido", onClick = { vm.cancelar(p) }, secundario = true)
                }
            }
        }
    }
}