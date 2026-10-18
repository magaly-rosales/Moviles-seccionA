package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.AppTopBar
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.CafeTextField
import com.rosales.cafecampus.ui.components.iconoDe
import com.rosales.cafecampus.viewmodel.MenuAdminViewModel

private val ICONOS = listOf("cafe", "plato", "jugo", "pan", "botella", "dulce")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormularioProductoScreen(
    vm: MenuAdminViewModel,
    productoId: Int?,
    onAtras: () -> Unit,
    onGuardado: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var disponible by rememberSaveable { mutableStateOf(true) }
    var icono by rememberSaveable { mutableStateOf("plato") }
    var cargado by rememberSaveable { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }


    if (productoId != null) {
        val existente by remember(productoId) { vm.producto(productoId) }.collectAsState(initial = null)
        LaunchedEffect(existente) {
            val p = existente
            if (p != null && !cargado) {
                nombre = p.nombre
                categoria = p.categoria
                precio = p.precio.toString()
                descripcion = p.descripcion
                disponible = p.disponible
                icono = p.icono
                cargado = true
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(if (productoId == null) "Nuevo producto" else "Editar producto", onAtras = onAtras)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CafeTextField(nombre, { nombre = it; error = null }, "Nombre *")
            CafeTextField(categoria, { categoria = it }, "Categoría (Bebidas, Comidas, Snacks…)")
            CafeTextField(
                precio, { precio = it; error = null }, "Precio (S/) *",
                teclado = KeyboardType.Decimal
            )
            CafeTextField(descripcion, { descripcion = it }, "Descripción", lineas = 3)

            Text("Ícono", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ICONOS.forEach { nombreIcono ->
                    FilterChip(
                        selected = icono == nombreIcono,
                        onClick = { icono = nombreIcono },
                        label = { Text(nombreIcono) },
                        leadingIcon = { Icon(iconoDe(nombreIcono), null, Modifier.size(18.dp)) }
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Disponible", modifier = Modifier.weight(1f))
                Switch(checked = disponible, onCheckedChange = { disponible = it })
            }

            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            CafeButton("Guardar", onClick = {
                error = vm.guardarFormulario(
                    productoId, nombre, categoria, precio, descripcion, disponible, icono
                )
                if (error == null) onGuardado()
            })
        }
    }
}