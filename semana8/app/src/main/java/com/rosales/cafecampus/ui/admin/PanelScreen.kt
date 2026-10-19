package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.data.local.relation.PedidosPorHora
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
    val pedidosHoy by vm.pedidosHoy.collectAsState()
    val pedidosPorHora by vm.pedidosPorHora.collectAsState()
    val pedidosNuevos by vm.pedidosNuevos.collectAsState()
    val cs = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Panel de control",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Cafetería · Hoy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = cs.onSurfaceVariant
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = cs.onSurfaceVariant
                    )
                }
            }
        }

        // 3 Tarjetas en fila
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ContadorEstado("Nuevos", nuevos, EstadoRecibido, Modifier.weight(1f))
                ContadorEstado("Preparando", preparando, EstadoPreparando, Modifier.weight(1f))
                ContadorEstado("Listos", listos, EstadoListo, Modifier.weight(1f))
            }
        }

        // Tarjeta Ventas del día
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Ventas del día",
                        style = MaterialTheme.typography.labelMedium,
                        color = cs.onPrimary.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = ventas.enSoles(),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = cs.onPrimary
                        )
                        Text(
                            text = "$pedidosHoy ${if (pedidosHoy == 1) "pedido" else "pedidos"}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = cs.onPrimary
                        )
                    }
                }
            }
        }

        // Gráfico de pedidos por hora
        item {
            GraficoPedidosPorHora(pedidosPorHora = pedidosPorHora)
        }

        // Sección Nuevos pedidos
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Nuevos pedidos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onVerPedidos) {
                    Text("Ver todos")
                }
            }
        }

        if (pedidosNuevos.isEmpty()) {
            item {
                Text(
                    "No hay pedidos nuevos",
                    color = cs.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            items(pedidosNuevos.take(5), key = { it.pedido.id }) { pc ->
                TarjetaNuevoPedido(pc = pc, onPreparar = { vm.preparar(pc) })
            }
        }
    }
}

@Composable
private fun ContadorEstado(etiqueta: String, valor: Int, color: Color, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$valor",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
private fun GraficoPedidosPorHora(pedidosPorHora: List<PedidosPorHora>) {
    val cs = MaterialTheme.colorScheme
    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Pedidos por hora",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))

            if (pedidosPorHora.isEmpty()) {
                Text(
                    "Aún no hay pedidos hoy",
                    color = cs.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            } else {
                val maxCantidad = pedidosPorHora.maxOfOrNull { it.cantidad } ?: 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    pedidosPorHora.forEach { p ->
                        val esMaximo = p.cantidad == maxCantidad
                        val colorBarra = if (esMaximo) cs.primary else cs.primary.copy(alpha = 0.35f)
                        val horaFormateada = "${p.hora.toIntOrNull() ?: p.hora}h"

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                text = "${p.cantidad}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = cs.onSurface
                            )
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height((80 * p.cantidad / maxCantidad).coerceAtLeast(12).dp)
                                    .background(
                                        color = colorBarra,
                                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                    )
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = horaFormateada,
                                style = MaterialTheme.typography.labelSmall,
                                color = cs.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaNuevoPedido(pc: PedidoConDetalle, onPreparar: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val idFormateado = "#%04d".format(pc.pedido.id)
    val nombreCliente = pc.cliente?.nombre?.abreviarNombre() ?: "Cliente"
    val primerDetalle = pc.detalles.firstOrNull()
    val resumenProducto = if (primerDetalle != null) "${primerDetalle.cantidad} x ${primerDetalle.nombreProducto}" else "Sin productos"
    val horaRecojo = pc.pedido.horaRecojo

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = idFormateado,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = cs.primary
                    )
                    Text(
                        text = nombreCliente,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$resumenProducto · $horaRecojo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = cs.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = onPreparar,
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Preparar", fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun String.abreviarNombre(): String {
    val partes = this.trim().split("\\s+".toRegex())
    if (partes.isEmpty()) return ""
    if (partes.size == 1) return partes[0]
    val primer = partes[0]
    val inicialSegundo = partes[1].firstOrNull()?.uppercaseChar() ?: ""
    return if (inicialSegundo != '\u0000') "$primer $inicialSegundo." else primer
}
