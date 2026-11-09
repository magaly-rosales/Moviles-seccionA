package com.tecsup.auramakeup.ui.lista

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.auramakeup.R
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando
import com.tecsup.auramakeup.ui.components.ErrorView
import com.tecsup.auramakeup.ui.components.ProductoCard
import com.tecsup.auramakeup.ui.theme.FondoLogo
import com.tecsup.auramakeup.ui.theme.FondoRosa
import java.io.File

data class CategoriaChip(val slug: String, val nombre: String)

private val categoriasFiltro = listOf(
    CategoriaChip("todos", "Todos"),
    CategoriaChip("beauty", "Maquillaje"),
    CategoriaChip("skin-care", "Cuidado de piel"),
    CategoriaChip("fragrances", "Perfumes")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    viewModel: ListaViewModel,
    esAdmin: Boolean = false,
    fotoPerfilRuta: String? = null,
    cantidadCarrito: Int = 0,
    onItemClick: (Int) -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onCarritoClick: () -> Unit = {},
    onAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val aviso by viewModel.aviso.collectAsStateWithLifecycle()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = FondoRosa,
        topBar = {
            Surface(
                color = FondoLogo,
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(88.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_aura),
                        contentDescription = "Aura Makeup",
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(72.dp)
                            .fillMaxWidth(0.55f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.padding(end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onFavoritosClick) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favoritos",
                                tint = Color(0xFFE91E63),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        if (!esAdmin) {
                            IconButton(onClick = onCarritoClick) {
                                if (cantidadCarrito > 0) {
                                    BadgedBox(
                                        badge = {
                                            Badge {
                                                Text(if (cantidadCarrito > 99) "99+" else cantidadCarrito.toString())
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ShoppingCart,
                                            contentDescription = "Carrito",
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.ShoppingCart,
                                        contentDescription = "Carrito",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                        if (esAdmin) {
                            IconButton(onClick = onAdminClick) {
                                Icon(
                                    imageVector = Icons.Filled.AdminPanelSettings,
                                    contentDescription = "Panel Admin",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        IconButton(onClick = onPerfilClick) {
                            val rutaLimpia = fotoPerfilRuta?.substringBefore("?")
                            if (rutaLimpia != null && File(rutaLimpia).exists()) {
                                key(fotoPerfilRuta) {
                                    AsyncImage(
                                        model = File(rutaLimpia),
                                        contentDescription = "Perfil",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Perfil",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }
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
                    categoriaSeleccionada = categoriaSeleccionada,
                    onCategoriaSelected = viewModel::seleccionarCategoria,
                    onItemClick = onItemClick,
                    onFavoritoClick = { viewModel.alternarFavorito(it) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListaContenido(
    items: List<Item>,
    aviso: String?,
    categoriaSeleccionada: String,
    onCategoriaSelected: (String) -> Unit,
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

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categoriasFiltro, key = { it.slug }) { cat ->
                val esSeleccionado = (categoriaSeleccionada == cat.slug)
                FilterChip(
                    selected = esSeleccionado,
                    onClick = { onCategoriaSelected(cat.slug) },
                    label = { Text(cat.nombre) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = esSeleccionado,
                        borderColor = MaterialTheme.colorScheme.outlineVariant,
                        selectedBorderColor = Color.Transparent
                    )
                )
            }
        }

        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay productos en esta categoría")
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
