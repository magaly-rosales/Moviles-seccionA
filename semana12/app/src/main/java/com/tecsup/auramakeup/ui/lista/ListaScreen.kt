package com.tecsup.auramakeup.ui.lista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando
import com.tecsup.auramakeup.ui.components.ErrorView
import com.tecsup.auramakeup.ui.components.ProductoCard
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    viewModel: ListaViewModel,
    esAdmin: Boolean = false,
    fotoPerfilRuta: String? = null,
    onItemClick: (Int) -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onCarritoClick: () -> Unit = {},
    onAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val aviso by viewModel.aviso.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Aura Makeup")
                        Text(
                            text = if (esAdmin) "Administrador" else "Cliente",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onFavoritosClick) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favoritos", tint = Color(0xFFE91E63))
                    }
                    IconButton(onClick = onCarritoClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }
                    if (esAdmin) {
                        IconButton(onClick = onAdminClick) {
                            Icon(Icons.Filled.AdminPanelSettings, contentDescription = "Panel Admin")
                        }
                    }
                    IconButton(onClick = onPerfilClick) {
                        val rutaLimpia = fotoPerfilRuta?.substringBefore("?")
                        if (rutaLimpia != null && File(rutaLimpia).exists()) {
                            androidx.compose.runtime.key(fotoPerfilRuta) {
                                AsyncImage(
                                    model = File(rutaLimpia),
                                    contentDescription = "Perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )
                            }
                        } else {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val e = estado) {
                is UiState.Cargando -> Cargando(texto = "Cargando productos...")
                is UiState.Error -> ErrorView(mensaje = e.mensaje, onReintentar = viewModel::refrescar)
                is UiState.Exito -> ListaContenido(
                    items = e.datos,
                    aviso = aviso,
                    onItemClick = onItemClick,
                    onFavoritoClick = { viewModel.alternarFavorito(it) }
                )
            }
        }
    }
}

@Composable
private fun ListaContenido(
    items: List<Item>,
    aviso: String?,
    onItemClick: (Int) -> Unit,
    onFavoritoClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        if (aviso != null) {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = aviso,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay productos")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    ProductoCard(
                        item = item,
                        onClick = { onItemClick(item.id) },
                        onFavorito = { onFavoritoClick(item.id) }
                    )
                }
            }
        }
    }
}
