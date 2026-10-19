package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.AppTopBar
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.CafeTextField
import com.rosales.cafecampus.ui.components.ProductoIcono
import com.rosales.cafecampus.ui.components.enSoles
import com.rosales.cafecampus.ui.theme.EstadoCancelado
import com.rosales.cafecampus.ui.theme.EstadoListo
import com.rosales.cafecampus.viewmodel.MenuViewModel

@Composable
fun DetalleProductoScreen(
    vm: MenuViewModel,
    productoId: Int,
    onAtras: () -> Unit,
    onAgregado: () -> Unit
) {
    val producto by remember(productoId) { vm.productoPorId(productoId) }.collectAsState(initial = null)
    var cantidad by rememberSaveable { mutableIntStateOf(1) }
    var nota by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar("Detalle del producto", onAtras = onAtras) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val p = producto
        if (p == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState()).padding(16.dp)
            ) {
                ProductoIcono(p.icono, Modifier.fillMaxWidth().height(180.dp), imagen = p.imagen)
                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        p.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    ChipDisponibilidad(p.disponible)
                }
                Text(p.categoria, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Text(
                    p.precio.enSoles(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(p.descripcion, style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(20.dp))
                Text("Cantidad", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedIconButton(onClick = { if (cantidad > 1) cantidad-- }, enabled = cantidad > 1) {
                        Icon(Icons.Filled.Remove, contentDescription = "Menos")
                    }
                    Text(
                        "$cantidad",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    OutlinedIconButton(onClick = { cantidad++ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Más")
                    }
                }

                Spacer(Modifier.height(16.dp))
                CafeTextField(valor = nota, onValor = { nota = it }, etiqueta = "Nota (opcional)", lineas = 2)

                Spacer(Modifier.height(24.dp))
                CafeButton(
                    texto = if (p.disponible) "Agregar al pedido · ${(p.precio * cantidad).enSoles()}"
                    else "Producto agotado",
                    onClick = {
                        vm.agregar(p, cantidad, nota)
                        onAgregado()
                    },
                    enabled = p.disponible
                )
            }
        }
    }
}

@Composable
private fun ChipDisponibilidad(disponible: Boolean) {
    val c = if (disponible) EstadoListo else EstadoCancelado
    Surface(color = c.copy(alpha = 0.15f), shape = RoundedCornerShape(50)) {
        Text(
            if (disponible) "Disponible" else "Agotado",
            color = c,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}