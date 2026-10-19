package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.ProductoCard
import com.rosales.cafecampus.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    vm: MenuViewModel,
    nombre: String? = null,
    onProducto: (Int) -> Unit,
    onCarrito: () -> Unit = {}
) {
    val productos by vm.productosFiltrados.collectAsState()
    val categorias by vm.categorias.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val enCarrito by vm.cantidadEnCarrito.collectAsState()
    val primerNombre = nombre?.substringBefore(" ")?.takeIf { it.isNotBlank() }
    val cs = MaterialTheme.colorScheme

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Saludo + bolsa
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        if (primerNombre != null) "¡Hola, $primerNombre!" else "¡Hola!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("¿Qué se te antoja hoy?", color = cs.onSurfaceVariant)
                }
                IconButton(onClick = onCarrito) {
                    BadgedBox(badge = { if (enCarrito > 0) Badge { Text("$enCarrito") } }) {
                        Icon(Icons.Filled.ShoppingBag, contentDescription = "Mi pedido")
                    }
                }
            }
        }

        // Categorías
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { c ->
                    FilterChip(
                        selected = categoria == c,
                        onClick = { vm.seleccionarCategoria(c) },
                        label = { Text(c) },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = cs.primary,
                            selectedLabelColor = cs.onPrimary
                        )
                    )
                }
            }
        }

        // Título
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                if (categoria == "Todo") "Populares hoy" else categoria,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 6.dp, top = 12.dp, bottom = 4.dp)
            )
        }

        if (productos.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "No hay productos en esta categoría",
                    color = cs.onSurfaceVariant,
                    modifier = Modifier.padding(6.dp)
                )
            }
        } else {
            items(productos, key = { it.id }) { p ->
                ProductoCard(
                    p = p,
                    onAgregar = { vm.agregar(p, 1, "") },
                    onClick = { onProducto(p.id) }
                )
            }
        }
    }
}