package com.tecsup.auramakeup.ui.carrito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.auramakeup.domain.model.ItemCarrito
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel,
    onVolver: () -> Unit,
    onPagar: () -> Unit
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val total by viewModel.total.collectAsStateWithLifecycle()
    var idAEliminar by rememberSaveable { mutableStateOf<Int?>(null) }

    val lista = (estado as? UiState.Exito)?.datos.orEmpty()
    val itemAEliminar = lista.find { it.productoId == idAEliminar }

    if (itemAEliminar != null) {
        AlertDialog(
            onDismissRequest = { idAEliminar = null },
            icon = {
                Icon(
                    imageVector = Icons.Filled.RemoveShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    text = "¿Quitar del carrito?",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Se quitará \"${itemAEliminar.titulo}\" de tu carrito.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminar(itemAEliminar.productoId)
                        idAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sí, quitar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { idAEliminar = null },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(24.dp)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi carrito") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (lista.isNotEmpty()) {
                Surface(shadowElevation = 8.dp) {
                    Column(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(16.dp)
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Text("Total", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = "$ %.2f".format(total),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = onPagar,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Ir a pagar")
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
                is UiState.Cargando -> Cargando()
                is UiState.Error -> Text(e.mensaje, Modifier.align(Alignment.Center))
                is UiState.Exito -> {
                    if (e.datos.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Tu carrito está vacío.\nAgrega productos desde el detalle.",
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(e.datos, key = { it.productoId }) { item ->
                                FilaCarrito(
                                    item = item,
                                    onMas = { viewModel.aumentar(item.productoId) },
                                    onMenos = {
                                        if (item.cantidad == 1) idAEliminar = item.productoId
                                        else viewModel.disminuir(item.productoId)
                                    },
                                    onEliminar = { idAEliminar = item.productoId }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaCarrito(
    item: ItemCarrito,
    onMas: () -> Unit,
    onMenos: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imagenUrl,
                contentDescription = item.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$ %.2f".format(item.subtotal),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FilledTonalIconButton(onClick = onMenos, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Filled.Remove, contentDescription = "Menos", modifier = Modifier.size(18.dp))
                    }
                    Text(
                        text = item.cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    FilledTonalIconButton(onClick = onMas, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Filled.Add, contentDescription = "Más", modifier = Modifier.size(18.dp))
                    }
                }
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
