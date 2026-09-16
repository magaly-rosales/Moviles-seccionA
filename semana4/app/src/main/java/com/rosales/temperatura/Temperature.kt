package com.rosales.temperatura
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Temperature() {
    var temperatura by remember { mutableStateOf(20) }
    val colorTexto = when {
        temperatura > 30 -> Color.Red
        temperatura < 10 -> Color.Blue
        else -> Color.Black
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "$temperatura°C", color = colorTexto)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { temperatura++ }) { Text("Subir") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { temperatura-- }) { Text("Bajar") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { temperatura = 20 }) { Text("Resetear") }
        }
    }
}