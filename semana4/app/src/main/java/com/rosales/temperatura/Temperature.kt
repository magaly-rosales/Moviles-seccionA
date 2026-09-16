package com.rosales.temperatura
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Temperature() {
    var temperatura by remember { mutableStateOf(20) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "$temperatura°C")
    }
}