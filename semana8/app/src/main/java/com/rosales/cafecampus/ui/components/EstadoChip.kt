package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.ui.theme.EstadoCancelado
import com.rosales.cafecampus.ui.theme.EstadoEntregado
import com.rosales.cafecampus.ui.theme.EstadoListo
import com.rosales.cafecampus.ui.theme.EstadoPreparando
import com.rosales.cafecampus.ui.theme.EstadoRecibido

fun EstadoPedido.colorEstado(): Color = when (this) {
    EstadoPedido.RECIBIDO -> EstadoRecibido
    EstadoPedido.PREPARANDO -> EstadoPreparando
    EstadoPedido.LISTO -> EstadoListo
    EstadoPedido.ENTREGADO -> EstadoEntregado
    EstadoPedido.CANCELADO -> EstadoCancelado
}

@Composable
fun EstadoChip(estado: EstadoPedido) {
    val c = estado.colorEstado()
    Surface(color = c.copy(alpha = 0.15f), shape = RoundedCornerShape(50)) {
        Text(
            estado.etiqueta,
            color = c,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}