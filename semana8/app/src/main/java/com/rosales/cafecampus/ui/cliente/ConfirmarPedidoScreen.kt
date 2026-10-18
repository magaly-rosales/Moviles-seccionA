package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.rosales.cafecampus.viewmodel.MenuViewModel
import com.rosales.cafecampus.viewmodel.PedidosClienteViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConfirmarPedidoScreen(
    menuVm: MenuViewModel,
    pedidosVm: PedidosClienteViewModel,
    onAtras: () -> Unit,
    onSeguirPidiendo: () -> Unit,
    onConfirmado: (Int) -> Unit
) {
    val carrito by menuVm.carrito.collectAsState()
    val horas = listOf("En 15 min", "12:00", "12:30", "13:00", "13:30")
    var hora by rememberSaveable { mutableStateOf(horas.first()) }
    var pago by rememberSaveable { mutableStateOf("CAJA") }
    var nota by rememberSaveable { mutableStateOf("") }
    var enviando by remember { mutableStateOf(false) }
    val total = menuVm.total()

    Scaffold(
        topBar = { AppTopBar("Confirmar pedido", onAtras = onAtras) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (carrito.isEmpty()) {
            Column(
                Modifier.fillMaxSize().padding(padding).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Tu pedido está vacío", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(16.dp))
                CafeButton("Ver el menú", onClick = onSeguirPidiendo)
            }
        } else {
            Column(
                Modifier.fillMaxSize().padding(padding)
                    .verticalScroll(rememberScrollState()).padding(16.dp)
            ) {
                Text("Resumen", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                carrito.forEach { item ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProductoIcono(item.producto.icono, Modifier.size(44.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(item.producto.nombre, fontWeight = FontWeight.SemiBold)
                            Text(
                                "${item.cantidad} x ${item.producto.precio.enSoles()}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (item.nota.isNotBlank()) {
                                Text(
                                    "“${item.nota}”",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(item.subtotal.enSoles(), fontWeight = FontWeight.Bold)
                        IconButton(onClick = { menuVm.quitar(item.producto.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Quitar")
                        }
                    }
                }

                HorizontalDivider(Modifier.padding(vertical = 12.dp))

                Text("Hora de recojo", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    horas.forEach { h ->
                        FilterChip(selected = hora == h, onClick = { hora = h }, label = { Text(h) })
                    }
                }

                Spacer(Modifier.height(12.dp))
                Text("Forma de pago", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = pago == "CAJA", onClick = { pago = "CAJA" }, label = { Text("Caja") })
                    FilterChip(selected = pago == "YAPE", onClick = { pago = "YAPE" }, label = { Text("Yape / Plin") })
                }

                Spacer(Modifier.height(12.dp))
                CafeTextField(
                    valor = nota,
                    onValor = { nota = it },
                    etiqueta = "Nota para la cafetería (opcional)",
                    lineas = 2
                )

                Spacer(Modifier.height(16.dp))
                FilaMonto("Subtotal", total.enSoles())
                FilaMonto("Total", total.enSoles(), destacado = true)

                Spacer(Modifier.height(20.dp))
                CafeButton(
                    texto = if (enviando) "Enviando..." else "Confirmar pedido",
                    onClick = {
                        enviando = true
                        pedidosVm.confirmar(carrito, hora, pago, nota) { id -> onConfirmado(id) }
                    },
                    enabled = !enviando
                )
                Spacer(Modifier.height(8.dp))
                CafeButton("Seguir pidiendo", onClick = onSeguirPidiendo, secundario = true)
            }
        }
    }
}

@Composable
private fun FilaMonto(etiqueta: String, valor: String, destacado: Boolean = false) {
    val estilo = if (destacado) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
    val peso = if (destacado) FontWeight.Bold else FontWeight.Normal
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(etiqueta, modifier = Modifier.weight(1f), style = estilo, fontWeight = peso)
        Text(
            valor,
            style = estilo,
            fontWeight = peso,
            color = if (destacado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}