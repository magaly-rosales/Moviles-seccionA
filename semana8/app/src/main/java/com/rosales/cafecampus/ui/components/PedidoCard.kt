package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.model.EstadoPedido

@Composable
fun PedidoCard(
    pc: PedidoConDetalle,
    onClick: () -> Unit = {},
    extra: @Composable ColumnScope.() -> Unit = {}
) {
    val estado = EstadoPedido.valueOf(pc.pedido.estado)
    val resumen = pc.detalles.joinToString { "${it.cantidad}x ${it.nombreProducto}" }

    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Pedido #${pc.pedido.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                EstadoChip(estado)
            }
            pc.cliente?.let {
                Text(it.nombre, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(6.dp))
            Text(resumen, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Recojo: ${pc.pedido.horaRecojo}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    pc.pedido.total.enSoles(),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            if (pc.pedido.nota.isNotBlank()) {
                Text(
                    "Nota: ${pc.pedido.nota}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            extra()
        }
    }
}