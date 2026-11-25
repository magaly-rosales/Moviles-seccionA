package com.tecsup.murocampus.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(
    vm: PostsViewModel,
    id: Int,
    onAtras: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    LaunchedEffect(id) {
        vm.abrir(id)
    }

    DisposableEffect(Unit) {
        onDispose {
            vm.limpiarBorrado()
        }
    }

    val estado by vm.detalle.collectAsStateWithLifecycle()
    val estadoBorrado by vm.borrado.collectAsStateWithLifecycle()

    LaunchedEffect(estadoBorrado) {
        when (val state = estadoBorrado) {
            is UiState.Exito -> {
                onAtras()
                snackbarHostState.showSnackbar("Publicación eliminada correctamente")
                vm.limpiarBorrado()
            }
            is UiState.Error -> {
                snackbarHostState.showSnackbar(state.mensaje)
                vm.limpiarBorrado()
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val curr = estado) {
            is UiState.Cargando -> Cargando(mensaje = "Cargando detalle…")
            is UiState.Error -> ErrorConReintento(
                mensaje = curr.mensaje,
                onReintentar = { vm.abrir(id) }
            )
            is UiState.Exito -> {
                val post = curr.datos.post
                val comentarios = curr.datos.comentarios

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Usuario ${post.userId}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    val estaBorrando = estadoBorrado is UiState.Cargando
                                    Button(
                                        onClick = { vm.borrar(post.id) },
                                        enabled = !estaBorrando,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.error,
                                            contentColor = MaterialTheme.colorScheme.onError
                                        )
                                    ) {
                                        if (estaBorrando) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                color = MaterialTheme.colorScheme.onError,
                                                strokeWidth = 2.dp
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Eliminando…")
                                        } else {
                                            Text("Eliminar")
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = post.body,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Comentarios (${comentarios.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(comentarios, key = { it.id }) { comentario ->
                        TarjetaComentario(comentario = comentario)
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaComentario(
    comentario: Comentario,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comentario.email,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comentario.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comentario.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
