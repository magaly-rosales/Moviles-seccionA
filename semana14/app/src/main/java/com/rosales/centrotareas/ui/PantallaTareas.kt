package com.rosales.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rosales.centrotareas.model.CargaUi
import com.rosales.centrotareas.model.PrimosUi
import com.rosales.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(vm: TareasViewModel) {
    val mensaje by vm.mensaje.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaTarea("Tarea con progreso") {
                Text("Pendiente")
            }
            TarjetaTarea("Contar números primos") {
                Text("Pendiente")
            }
            TarjetaTarea("Dos cargas") {
                Text("Pendiente")
            }
        }
    }
}

@Composable
private fun TarjetaTarea(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                titulo,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            contenido()
        }
    }
}

@Composable
private fun RecuadroExito(texto: String) {
    Text(
        text = texto,
        color = Color(0xFF1B5E20),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
            .padding(12.dp)
    )
}

@Composable
private fun RecuadroError(texto: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(texto, color = MaterialTheme.colorScheme.onErrorContainer)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onReintentar) { Text("Reintentar") }
    }
}
