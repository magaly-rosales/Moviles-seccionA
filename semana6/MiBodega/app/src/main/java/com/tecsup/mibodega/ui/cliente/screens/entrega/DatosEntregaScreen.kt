package com.tecsup.mibodega.ui.cliente.screens.entrega


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        Text("Datos de entrega")
        Button(onClick = onVolver) { Text("Volver") }
        Button(onClick = onConfirmar) { Text("Confirmar pedido") }
    }
}


