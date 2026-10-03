package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BarraNavegacion
import com.tecsup.mibodega.ui.componentes.DestinoBarra
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega
import java.text.Normalizer

private enum class OrdenPrecio(val etiqueta: String, val corta: String) {
    SIN_ORDEN("Sin ordenar", "Ordenar"),
    MENOR_MAYOR("Precio: menor a mayor", "Precio ↑"),
    MAYOR_MENOR("Precio: mayor a menor", "Precio ↓")
}

private fun String.quitarAcentos(): String {
    val unaccented = Normalizer.normalize(this, Normalizer.Form.NFD)
    return Regex("\\p{InCombiningDiacriticalMarks}+").replace(unaccented, "")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    productos: List<Producto> = listaProductosFake,
    cantidadCarrito: Int,
    favoritos: Set<Int>,
    onVerCarrito: () -> Unit,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onFavorito: (Producto) -> Unit,
    onNavegar: (DestinoBarra) -> Unit
) {
    var categoriaSeleccionada by remember { mutableStateOf(listaCategorias.first()) }
    var textoBusqueda by remember { mutableStateOf("") }
    var orden by remember { mutableStateOf(OrdenPrecio.SIN_ORDEN) }
    var menuOrdenAbierto by remember { mutableStateOf(false) }

    val queryNormalizada = textoBusqueda.trim().quitarAcentos().lowercase()

    val productosFiltrados = productos.filter { producto ->
        val coincideCategoria = categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada
        val nombreNorm = producto.nombre.quitarAcentos().lowercase()
        val coincideBusqueda = queryNormalizada.isEmpty() || nombreNorm.contains(queryNormalizada)
        coincideCategoria && coincideBusqueda
    }

    val productosAMostrar = when (orden) {
        OrdenPrecio.SIN_ORDEN -> productosFiltrados
        OrdenPrecio.MENOR_MAYOR -> productosFiltrados.sortedBy { it.precio }
        OrdenPrecio.MAYOR_MENOR -> productosFiltrados.sortedByDescending { it.precio }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            append("Mi ")
                            withStyle(SpanStyle(color = VerdeBodega)) { append("Bodega") }
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onVerCarrito) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge(containerColor = RojoPrecio) {
                                        Text("$cantidadCarrito", color = Color.White)
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BarraNavegacion(actual = DestinoBarra.INICIO, onNavegar = onNavegar)
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(horizontal = 16.dp)
        ) {
            // Buscador con lupa y botón X
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                placeholder = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar búsqueda")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = VerdeBodega
                )
            )

            Spacer(Modifier.height(12.dp))

            // Categorías con ícono arriba y texto abajo
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(listaCategorias) { categoria ->
                    val icono = when (categoria) {
                        "Todos" -> Icons.Default.Apps
                        "Bebidas" -> Icons.Default.LocalBar
                        "Abarrotes" -> Icons.Default.ShoppingBag
                        "Snacks" -> Icons.Default.Fastfood
                        else -> Icons.Default.Apps
                    }
                    FichaCategoria(
                        texto = categoria,
                        icono = icono,
                        seleccionado = categoria == categoriaSeleccionada,
                        onClick = { categoriaSeleccionada = categoria }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Productos destacados + dropdown de orden
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Productos destacados",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Box {
                    TextButton(onClick = { menuOrdenAbierto = true }) {
                        Text(orden.corta, color = VerdeBodega, fontWeight = FontWeight.SemiBold)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Ordenar por precio",
                            tint = VerdeBodega
                        )
                    }
                    DropdownMenu(
                        expanded = menuOrdenAbierto,
                        onDismissRequest = { menuOrdenAbierto = false }
                    ) {
                        OrdenPrecio.entries.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion.etiqueta) },
                                onClick = {
                                    orden = opcion
                                    menuOrdenAbierto = false
                                }
                            )
                        }
                    }
                }
            }

            // Si no hay productos (estado vacío) o Grid de productos
            if (productosAMostrar.isEmpty()) {
                EstadoBusquedaVacio(modifier = Modifier.weight(1f))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(productosAMostrar, key = { it.id }) { producto ->
                        ProductoCard(
                            producto = producto,
                            esFavorito = producto.id in favoritos,
                            onClick = { onProductoClick(producto) },
                            onAgregar = { onAgregarProducto(producto) },
                            onFavorito = { onFavorito(producto) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FichaCategoria(
    texto: String,
    icono: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.surfaceVariant
    val colorContenido = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .width(76.dp)
            .background(fondo, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icono,
            contentDescription = texto,
            tint = colorContenido,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = texto,
            color = colorContenido,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EstadoBusquedaVacio(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "No encontramos productos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Intenta buscar con otros términos o cambia de categoría.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InicioPreview() {
    BodegaTheme {
        InicioScreen(
            cantidadCarrito = 3,
            favoritos = emptySet(),
            onVerCarrito = {},
            onProductoClick = {},
            onAgregarProducto = {},
            onFavorito = {},
            onNavegar = {}
        )
    }
}
