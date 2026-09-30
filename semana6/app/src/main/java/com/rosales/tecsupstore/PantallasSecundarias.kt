package com.rosales.tecsupstore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PantallaPedidos() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Mis pedidos") }
}

@Composable
fun PantallaFavoritos() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Favoritos") }
}

@Composable
fun PantallaPerfil() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Perfil") }
}