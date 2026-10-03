package com.tecsup.mibodega.ui.cliente.screens.registro

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.AzulEnlace
import com.tecsup.mibodega.ui.theme.BodegaTheme

@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onCrearCuenta: (nombre: String, telefono: String, direccion: String, referencia: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var intentoEnviar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoRegistro(onVolver = onVolver)

        Spacer(Modifier.height(20.dp))

        // Avatar circular celeste con ícono de persona e insignia azul con "+" abajo a la derecha
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(80.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFD6E8FA), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar persona",
                        tint = AzulEnlace,
                        modifier = Modifier.size(44.dp)
                    )
                }

                // Insignia con "+" abajo a la derecha
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .background(AzulEnlace, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar foto",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Nombre completo",
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
            etiqueta = "Dirección de entrega",
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

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Crear cuenta",
            onClick = {
                intentoEnviar = true
                val completo = nombre.isNotBlank() && telefono.isNotBlank() &&
                        direccion.isNotBlank() && referencia.isNotBlank()
                if (completo) onCrearCuenta(nombre, telefono, direccion, referencia)
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EncabezadoRegistro(onVolver: () -> Unit) {
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
            text = "Crear cuenta",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
    Text(
        text = "Completa tus datos para continuar",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroPreview() {
    BodegaTheme {
        RegistroScreen(onVolver = {}, onCrearCuenta = { _, _, _, _ -> })
    }
}
