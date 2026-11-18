package com.rosales.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rosales.centrotareas.model.CargaUi
import com.rosales.centrotareas.model.PrimosUi
import com.rosales.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(vm: TareasViewModel) {
    val mensaje by vm.mensaje.collectAsState()
    val progreso by vm.progreso.collectAsState()
    val corriendo by vm.corriendo.collectAsState()
    val primos by vm.primos.collectAsState()
    val progresoPrimos by vm.progresoPrimos.collectAsState()
    val carga by vm.carga.collectAsState()
    val simularError by vm.simularError.collectAsState()
    val tiempos by vm.tiempos.collectAsState()
    val promedioUnaPorUna by vm.promedioUnaPorUna.collectAsState()
    val promedioALaVez by vm.promedioALaVez.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaTarea("Tarea con progreso") {
                Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { vm.tareaLarga() }, enabled = !corriendo) { Text("Iniciar") }
                    OutlinedButton(onClick = { vm.cancelar() }, enabled = corriendo) { Text("Cancelar") }
                }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${(progreso * 100).toInt()} %")
                Text(mensaje)
            }
            TarjetaTarea("Contar números primos") {
                var texto by rememberSaveable { mutableStateOf("500000") }
                val numero = texto.toIntOrNull()
                val calculando = primos is PrimosUi.Calculando
                OutlinedTextField(
                    value = texto,
                    onValueChange = { nuevo -> texto = nuevo.filter { it.isDigit() } },
                    label = { Text("Contar primos hasta") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { numero?.let { vm.contar(it) } },
                        enabled = !calculando && numero != null && numero >= 2
                    ) { Text("Contar") }
                    OutlinedButton(onClick = { vm.cancelarPrimos() }, enabled = calculando) {
                        Text("Cancelar")
                    }
                }
                when (val p = primos) {
                    PrimosUi.Inicial -> Unit
                    PrimosUi.Calculando -> Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        LinearProgressIndicator(
                            progress = { progresoPrimos },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("${(progresoPrimos * 100).toInt()} %")
                    }
                    is PrimosUi.Listo -> RecuadroExito(
                        "Primos encontrados: ${p.cantidad}\nTiempo: ${p.milisegundos} ms"
                    )
                    PrimosUi.Cancelado -> Text("Cancelado", color = MaterialTheme.colorScheme.error)
                }
            }
            TarjetaTarea("Dos cargas") {
                val cargando = carga is CargaUi.Cargando || carga is CargaUi.Reintentando
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { vm.cargarUnaPorUna() }, enabled = !cargando) {
                        Text("Una por una")
                    }
                    Button(onClick = { vm.cargarALaVez() }, enabled = !cargando) { Text("A la vez") }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = simularError, onCheckedChange = { vm.cambiarSimularError(it) })
                    Spacer(Modifier.width(8.dp))
                    Text("Simular error")
                }
                when (val c = carga) {
                    CargaUi.Inicial -> Unit
                    CargaUi.Cargando -> CircularProgressIndicator()
                    is CargaUi.Reintentando -> Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(8.dp))
                        Text("Reintentando (${c.intento}/3)...")
                    }
                    is CargaUi.Exito -> RecuadroExito(
                        "Modo: ${c.modo}\nPerfil: ${c.perfil}\nPedidos: ${c.pedidos}\nTiempo: ${c.milisegundos} ms"
                    )
                    is CargaUi.Error -> RecuadroError(c.mensaje) { vm.cargarALaVez() }
                }
                if (tiempos.isNotEmpty()) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        "Historial de tiempos",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        tiempos.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.modo)
                                Text("${item.milisegundos} ms")
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Promedio Una por una:", fontWeight = FontWeight.SemiBold)
                            Text("${promedioUnaPorUna.toInt()} ms", fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Promedio A la vez:", fontWeight = FontWeight.SemiBold)
                            Text("${promedioALaVez.toInt()} ms", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    TextButton(
                        onClick = { vm.limpiarTiempos() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Limpiar")
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaTarea(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                titulo,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            contenido()
        }
    }
}

@Composable
private fun RecuadroExito(texto: String) {
    Text(
        text = texto,
        color = Color(0xFF1B5E20),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
            .padding(12.dp)
    )
}

@Composable
private fun RecuadroError(texto: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(texto, color = MaterialTheme.colorScheme.onErrorContainer)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onReintentar) { Text("Reintentar") }
    }
}
