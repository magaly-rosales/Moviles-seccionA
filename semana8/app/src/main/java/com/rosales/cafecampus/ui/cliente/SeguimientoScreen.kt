package com.rosales.cafecampus.ui.cliente

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
import com.rosales.cafecampus.ui.components.EstadoChip
import com.rosales.cafecampus.ui.components.StepperEstado
import com.rosales.cafecampus.ui.components.enSoles
import com.rosales.cafecampus.viewmodel.PedidosClienteViewModel

@Composable
fun SeguimientoScreen(vm: PedidosClienteViewModel, pedidoId: Int, onAtras: () -> Unit) {

    val pc by remember(pedidoId) { vm.pedido(pedidoId) }.collectAsState()

    Scaffold(
        topBar = { AppTopBar("Seguimiento", onAtras = onAtras) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val p = pc
        if (p == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val estado = EstadoPedido.valueOf(p.pedido.estado)
            Column(
                Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState()).padding(16.dp)
            ) {
                OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Pedido #${p.pedido.id}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            EstadoChip(estado)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Recojo: ${p.pedido.horaRecojo} · Pago: ${if (p.pedido.pago == "YAPE") "Yape / Plin" else "Caja"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Total: ${p.pedido.total.enSoles()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text("Estado de tu pedido", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                StepperEstado(estado)

                Spacer(Modifier.height(24.dp))
                Text("Detalle", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                p.detalles.forEach { d ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text("${d.cantidad}x ${d.nombreProducto}", modifier = Modifier.weight(1f))
                        Text((d.precioUnitario * d.cantidad).enSoles())
                    }
                }
                if (p.pedido.nota.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Nota: ${p.pedido.nota}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}