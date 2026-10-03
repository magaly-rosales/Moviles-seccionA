package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.MetodoPago
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.cliente.modelo.TipoEntrega
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun ConfirmacionScreen(
    pedido: Pedido?,
    onVerEstadoPedido: () -> Unit,
    onVolverInicio: () -> Unit
) {
    val numeroPedido = pedido?.numero ?: 1001
    val total = pedido?.total ?: 25.90
    val direccion = pedido?.direccion.takeIf { !it.isNullOrBlank() } ?: "Av. Los Olivos 123"
    val referencia = pedido?.referencia.takeIf { !it.isNullOrBlank() } ?: "Frente al parque"
    val metodoPago = pedido?.metodoPago?.etiqueta ?: MetodoPago.EFECTIVO.etiqueta

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        // Círculo verde grande con check blanco y confeti alrededor con Canvas
        Box(
            modifier = Modifier.size(130.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val lineLength = 12.dp.toPx()
                val innerRadius = 46.dp.toPx()
                val confettiColors = listOf(
                    Color(0xFFFFC107), Color(0xFF2196F3), Color(0xFFE91E63),
                    Color(0xFF4CAF50), Color(0xFFFF9800), Color(0xFF9C27B0),
                    Color(0xFFFFC107), Color(0xFF00BCD4), Color(0xFFE91E63)
                )
                val angles = listOf(0f, 40f, 80f, 120f, 160f, 200f, 240f, 280f, 320f)
                angles.forEachIndexed { index, angleDeg ->
                    val rad = Math.toRadians(angleDeg.toDouble())
                    val startX = center.x + innerRadius * Math.cos(rad).toFloat()
                    val startY = center.y + innerRadius * Math.sin(rad).toFloat()
                    val endX = center.x + (innerRadius + lineLength) * Math.cos(rad).toFloat()
                    val endY = center.y + (innerRadius + lineLength) * Math.sin(rad).toFloat()
                    drawLine(
                        color = confettiColors[index % confettiColors.size],
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 3.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(VerdeBodega, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Pedido realizado",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "¡Pedido realizado!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeBodega
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Tu pedido está siendo preparado y\nserá entregado pronto.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        // Tarjeta resumen del pedido
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Pedido #$numeroPedido",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "S/ %.2f".format(total),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RojoPrecio
                    )
                }

                Spacer(Modifier.height(8.dp))

                Column {
                    Text(
                        text = "Dirección",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$direccion ($referencia)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(8.dp))

                Column {
                    Text(
                        text = "Método de pago",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = metodoPago,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        // Botón con borde verde "Ver estado del pedido"
        OutlinedButton(
            onClick = onVerEstadoPedido,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, VerdeBodega),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = VerdeBodega
            )
        ) {
            Icon(
                imageVector = Icons.Default.Receipt,
                contentDescription = null,
                tint = VerdeBodega,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Ver estado del pedido",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = VerdeBodega
            )
        }

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Volver al inicio",
            onClick = onVolverInicio
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConfirmacionPreview() {
    BodegaTheme {
        ConfirmacionScreen(
            pedido = Pedido(
                numero = 1024,
                fecha = "24/10/2024 10:00",
                items = listOf(ItemCarrito(listaProductosFake[4], 1)),
                tipoEntrega = TipoEntrega.DELIVERY,
                total = 25.90,
                direccion = "Av. Los Olivos 123",
                referencia = "Frente al parque",
                metodoPago = MetodoPago.EFECTIVO
            ),
            onVerEstadoPedido = {},
            onVolverInicio = {}
        )
    }
}
