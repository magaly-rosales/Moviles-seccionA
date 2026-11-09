package com.tecsup.auramakeup.ui.admin
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.auramakeup.ui.UiState
import com.tecsup.auramakeup.ui.components.Cargando

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val ocupado by viewModel.ocupado.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    var confirmarLimpieza by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbar.showSnackbar(it)
            viewModel.mensajeMostrado()
        }
    }

    if (confirmarLimpieza) {
        AlertDialog(
            onDismissRequest = { confirmarLimpieza = false },
            icon = {
                Icon(
                    imageVector = Icons.Filled.DeleteSweep,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    text = "¿Limpiar caché?",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Se borrarán todos los productos guardados en el celular, incluidos los favoritos.\n\nPodrás recuperarlos con Sincronizar.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        confirmarLimpieza = false
                        viewModel.limpiarCache()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sí, limpiar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { confirmarLimpieza = false },
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
                title = { Text("Panel Admin") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val e = estado) {
                is UiState.Cargando -> Cargando()
                is UiState.Error -> Text(e.mensaje, Modifier.align(Alignment.Center))
                is UiState.Exito -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TarjetaTotal(
                            titulo = "Productos guardados",
                            valor = e.datos.totalGuardados,
                            icono = Icons.Filled.Inventory2,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        TarjetaTotal(
                            titulo = "Favoritos",
                            valor = e.datos.totalFavoritos,
                            icono = Icons.Filled.Favorite,
                            color = Color(0xFFE91E63),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = viewModel::sincronizar,
                        enabled = !ocupado,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (ocupado) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(Icons.Filled.Sync, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Sincronizar")
                        }
                    }
                    OutlinedButton(
                        onClick = { confirmarLimpieza = true },
                        enabled = !ocupado,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(Icons.Filled.DeleteSweep, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Limpiar caché")
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaTotal(
    titulo: String,
    valor: Int,
    icono: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}