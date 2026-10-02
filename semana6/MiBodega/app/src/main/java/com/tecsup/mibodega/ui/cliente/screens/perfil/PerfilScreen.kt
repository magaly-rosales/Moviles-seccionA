package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tecsup.mibodega.ui.componentes.BarraNavegacion
import com.tecsup.mibodega.ui.componentes.DestinoBarra

@Composable
fun PerfilScreen(
    onNavegar: (DestinoBarra) -> Unit
) {
    Scaffold(
        bottomBar = { BarraNavegacion(actual = DestinoBarra.PERFIL, onNavegar = onNavegar) }
    ) { paddingInterno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno),
            contentAlignment = Alignment.Center
        ) {
            Text("Mi perfil", style = MaterialTheme.typography.titleLarge)
        }
    }
}