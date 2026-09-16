package com.rosales.temperatura
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
data class Tarea(
    val texto: String,
    var completada: Boolean = false
)
@Composable
fun ListaTareasScreen() {
    var tareas by remember { mutableStateOf(listOf<Tarea>()) }
    var textoNuevaTarea by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Mis Tareas", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total: ${tareas.size} | Completadas: ${tareas.count { it.completada }}")
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            OutlinedTextField(
                value = textoNuevaTarea,
                onValueChange = { textoNuevaTarea = it },
                label = { Text("Nueva tarea") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (textoNuevaTarea.isNotBlank()) {
                    tareas = tareas + Tarea(texto = textoNuevaTarea)
                    textoNuevaTarea = ""
                }
            }) {
                Text("Agregar")
            }
        }
    }
}