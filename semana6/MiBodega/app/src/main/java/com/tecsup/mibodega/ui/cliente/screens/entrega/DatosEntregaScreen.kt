package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.MetodoPago
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmar: (nombre: String, telefono: String, direccion: String, referencia: String, metodoPago: MetodoPago) -> Unit
) {
    var nombre by remember { mutableStateOf("Juan Pérez") }
    var telefono by remember { mutableStateOf("987 654 321") }
    var direccion by remember { mutableStateOf("Av. Los Olivos 123") }
    var referencia by remember { mutableStateOf("Frente al parque") }
    var metodoPago by remember { mutableStateOf(MetodoPago.EFECTIVO) }
    var intentoEnviar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            IconButton(
                onClick = onVolver,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Nombre",
            valor = nombre,
            onValorCambia = { nombre = it },
            placeholder = "Juan Pérez",
            isError = intentoEnviar && nombre.isBlank()
        )
        Spacer(Modifier.height(14.dp))

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            onValorCambia = { telefono = it },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone,
            isError = intentoEnviar && telefono.isBlank()
        )
        Spacer(Modifier.height(14.dp))

        CampoTexto(
            etiqueta = "Dirección",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Av. Los Olivos 123",
            isError = intentoEnviar && direccion.isBlank()
        )
        Spacer(Modifier.height(14.dp))

        CampoTexto(
            etiqueta = "Referencia",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Frente al parque",
            isError = intentoEnviar && referencia.isBlank()
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Método de pago",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        // Opciones de Método de Pago (Efectivo, Yape, Plin)
        OpcionMetodoPago(
            metodo = MetodoPago.EFECTIVO,
            seleccionado = metodoPago == MetodoPago.EFECTIVO,
            onSeleccionar = { metodoPago = MetodoPago.EFECTIVO }
        )

        OpcionMetodoPago(
            metodo = MetodoPago.YAPE,
            seleccionado = metodoPago == MetodoPago.YAPE,
            onSeleccionar = { metodoPago = MetodoPago.YAPE }
        )

        OpcionMetodoPago(
            metodo = MetodoPago.PLIN,
            seleccionado = metodoPago == MetodoPago.PLIN,
            onSeleccionar = { metodoPago = MetodoPago.PLIN }
        )

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Confirmar pedido",
            onClick = {
                intentoEnviar = true
                val completo = nombre.isNotBlank() && telefono.isNotBlank() &&
                        direccion.isNotBlank() && referencia.isNotBlank()
                if (completo) {
                    onConfirmar(nombre, telefono, direccion, referencia, metodoPago)
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun OpcionMetodoPago(
    metodo: MetodoPago,
    seleccionado: Boolean,
    onSeleccionar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = seleccionado,
                onClick = onSeleccionar,
                role = Role.RadioButton
            )
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = seleccionado,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
        )

        Spacer(Modifier.width(8.dp))

        when (metodo) {
            MetodoPago.EFECTIVO -> {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = null,
                    tint = VerdeBodega,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Efectivo al entregar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            MetodoPago.YAPE -> {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF732282), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "yape",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Yape",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            MetodoPago.PLIN -> {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF00C7DE), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "plin",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Plin",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(onVolver = {}, onConfirmar = { _, _, _, _, _ -> })
    }
}
