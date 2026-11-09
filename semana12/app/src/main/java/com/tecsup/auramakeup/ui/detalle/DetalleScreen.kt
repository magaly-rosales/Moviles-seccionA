package com.tecsup.auramakeup.ui.detalle

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando
import com.tecsup.auramakeup.ui.components.ErrorView
import com.tecsup.auramakeup.ui.components.categoriaEnEspanol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    viewModel: DetalleViewModel,
    esAdmin: Boolean = false,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbar.showSnackbar(it)
            viewModel.mensajeMostrado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    val actual = estado
                    if (actual is UiState.Exito) {
                        IconButton(onClick = viewModel::alternarFavorito) {
                            Icon(
                                imageVector = if (actual.datos.esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (actual.datos.esFavorito) Color(0xFFE91E63) else Color.Gray
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (!esAdmin && estado is UiState.Exito) {
                Surface(shadowElevation = 8.dp) {
                    Button(
                        onClick = viewModel::agregarAlCarrito,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Filled.AddShoppingCart, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Agregar al carrito")
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val e = estado) {
                is UiState.Cargando -> Cargando(texto = "Cargando detalle...")
                is UiState.Error -> ErrorView(mensaje = e.mensaje, onReintentar = viewModel::cargar)
                is UiState.Exito -> DetalleContenido(e.datos)
            }
        }
    }
}

@Composable
private fun DetalleContenido(item: Item) {
    val fotos = item.imagenes.ifEmpty { listOfNotNull(item.imagenUrl) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(fotos) { url ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    AsyncImage(
                        model = url,
                        contentDescription = item.titulo,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(260.dp)
                    )
                }
            }
        }

        Column(Modifier.padding(horizontal = 16.dp)) {
            AssistChip(onClick = {}, label = { Text(categoriaEnEspanol(item.categoria)) })
            Spacer(Modifier.height(4.dp))
            Text(item.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(item.marca, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$ %.2f".format(item.precio),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFB300))
                Spacer(Modifier.width(4.dp))
                Text("%.1f".format(item.rating), style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (item.stock > 0) "Stock: ${item.stock} unidades" else "Agotado",
                style = MaterialTheme.typography.bodyMedium,
                color = if (item.stock > 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(16.dp))
            Text("Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(item.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))
        }
    }
}
