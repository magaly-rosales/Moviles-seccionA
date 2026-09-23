package com.rosales.temperatura

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items

data class Tarea(
    val texto: String,
    var completada: Boolean = false
)

val TareaSaver = listSaver<List<Tarea>, Any>(
    save = { lista -> lista.flatMap { listOf(it.texto, it.completada) } },
    restore = { guardado ->
        guardado.chunked(2).map {
            Tarea(texto = it[0] as String, completada = it[1] as Boolean)
        }
    }
)

@Composable
fun TareaItem(
    tarea: Tarea,
    onCompletadaChange: (Boolean) -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = tarea.completada,
            onCheckedChange = onCompletadaChange
        )
        Text(
            text = tarea.texto,
            modifier = Modifier.weight(1f),
            textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None
        )
        Button(onClick = onEliminar) {
            Text("X")
        }
    }
}

@Composable
fun ListaTareasScreen() {
    var tareas by rememberSaveable(stateSaver = TareaSaver) { mutableStateOf(listOf<Tarea>()) }
    var textoNuevaTarea by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Tareas",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Total: ${tareas.size} | Completadas: ${tareas.count { it.completada }}")
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = textoNuevaTarea,
                onValueChange = { textoNuevaTarea = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.weight(1f)
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
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tareas) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onCompletadaChange = { nuevoValor ->
                        tareas = tareas.map {
                            if (it == tarea) it.copy(completada = nuevoValor) else it
                        }
                    },
                    onEliminar = {
                        tareas = tareas.filter { it != tarea }
                    }
                )
            }
        }
    }
}