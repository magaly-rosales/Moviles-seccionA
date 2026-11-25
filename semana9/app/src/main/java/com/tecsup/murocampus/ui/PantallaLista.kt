package com.tecsup.murocampus.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.ui.theme.colorUsuario
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(
    vm: PostsViewModel,
    onPostClick: (Int) -> Unit,
    onCrear: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val usuarioFiltro by vm.usuarioFiltro.collectAsStateWithLifecycle()
    val textoBusqueda by vm.textoBusqueda.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = vm::buscar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar por título…") },
                singleLine = true,
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { vm.buscar("") }) {
                            Text(
                                text = "✕",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    val selected = (usuarioFiltro == null)
                    val chipInteraction = remember { MutableInteractionSource() }
                    val isHovered by chipInteraction.collectIsHoveredAsState()
                    val animatedContainer by animateColorAsState(
                        targetValue = when {
                            selected -> MaterialTheme.colorScheme.primaryContainer
                            isHovered -> MaterialTheme.colorScheme.surfaceVariant
                            else -> MaterialTheme.colorScheme.surface
                        },
                        label = "TodosChipContainer"
                    )

                    FilterChip(
                        selected = selected,
                        onClick = { vm.filtrar(null) },
                        interactionSource = chipInteraction,
                        modifier = Modifier.hoverable(chipInteraction),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = animatedContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSurface,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        label = { Text("Todos", fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) }
                    )
                }

                items((1..10).toList()) { id ->
                    val selected = (usuarioFiltro == id)
                    val (userContainer, userContent) = colorUsuario(id)
                    val chipInteraction = remember { MutableInteractionSource() }
                    val isHovered by chipInteraction.collectIsHoveredAsState()

                    val animatedContainer by animateColorAsState(
                        targetValue = when {
                            selected -> userContainer
                            isHovered -> userContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surface
                        },
                        label = "UserChipContainer"
                    )

                    val animatedBorderColor by animateColorAsState(
                        targetValue = when {
                            selected -> userContent
                            isHovered -> userContent.copy(alpha = 0.6f)
                            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        },
                        label = "UserChipBorder"
                    )

                    FilterChip(
                        selected = selected,
                        onClick = { vm.filtrar(id) },
                        interactionSource = chipInteraction,
                        modifier = Modifier.hoverable(chipInteraction),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = animatedContainer,
                            selectedContainerColor = userContainer,
                            labelColor = if (selected) userContent else MaterialTheme.colorScheme.onSurface,
                            selectedLabelColor = userContent
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            borderColor = animatedBorderColor,
                            selectedBorderColor = userContent,
                            borderWidth = if (selected || isHovered) 2.dp else 1.dp
                        ),
                        label = { Text("Usuario $id", fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (val currEstado = estado) {
                    is UiState.Cargando -> Cargando()
                    is UiState.Error -> ErrorConReintento(
                        mensaje = currEstado.mensaje,
                        onReintentar = vm::cargar
                    )
                    is UiState.Exito -> {
                        if (currEstado.datos.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sin publicaciones",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 80.dp)
                            ) {
                                items(currEstado.datos, key = { it.id }) { post ->
                                    TarjetaPost(
                                        post = post,
                                        onClick = { onPostClick(post.id) },
                                        modifier = Modifier.animateItem()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onCrear,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Text(
                text = "+",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
