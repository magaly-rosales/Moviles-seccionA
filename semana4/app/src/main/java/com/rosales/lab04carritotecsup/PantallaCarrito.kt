package com.rosales.lab04carritotecsup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaCarrito() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    val productos = remember { mutableStateListOf<Producto>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Mi Carrito TECSUP", style = MaterialTheme.typography.headlineSmall)
    }
}