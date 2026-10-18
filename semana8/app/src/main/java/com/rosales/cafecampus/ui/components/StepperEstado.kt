package com.rosales.cafecampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.EstadoPedido

@Composable
fun StepperEstado(actual: EstadoPedido) {
    if (actual == EstadoPedido.CANCELADO) {
        EstadoChip(actual)
        return
    }
    val pasos = listOf(
        EstadoPedido.RECIBIDO, EstadoPedido.PREPARANDO,
        EstadoPedido.LISTO, EstadoPedido.ENTREGADO
    )
    val indice = pasos.indexOf(actual)
    val cs = MaterialTheme.colorScheme

    Column {
        pasos.forEachIndexed { i, paso ->
            val hecho = i <= indice
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(28.dp).clip(CircleShape)
                        .background(if (hecho) cs.primary else cs.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (i < indice) {
                        Icon(Icons.Filled.Check, null, tint = cs.onPrimary, modifier = Modifier.size(16.dp))
                    } else {
                        Text(
                            "${i + 1}",
                            color = if (hecho) cs.onPrimary else cs.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    paso.etiqueta,
                    fontWeight = if (i == indice) FontWeight.Bold else FontWeight.Normal,
                    color = if (hecho) cs.onSurface else cs.onSurfaceVariant
                )
            }
            if (i < pasos.lastIndex) {
                Box(
                    Modifier.padding(start = 13.dp).width(2.dp).height(20.dp)
                        .background(if (i < indice) cs.primary else cs.surfaceVariant)
                )
            }
        }
    }
}