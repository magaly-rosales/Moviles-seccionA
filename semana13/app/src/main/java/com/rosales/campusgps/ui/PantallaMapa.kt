package com.rosales.campusgps.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.rosales.campusgps.model.UbicacionUi
import com.rosales.campusgps.util.formatearDistancia
import com.rosales.campusgps.viewmodel.MapaViewModel

private fun tienePermiso(contexto: Context): Boolean =
    ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
    ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val todosLosLugares by vm.lugares.collectAsState()
    val lugaresFiltrados by vm.lugaresFiltrados.collectAsState()
    val categoriaSeleccionada by vm.categoriaSeleccionada.collectAsState()
    val radioMetros by vm.radioMetros.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val contexto = LocalContext.current

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        if (tienePermiso(contexto)) vm.buscar()
        else lanzador.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            MapaUbicacion(
                estado = estado,
                lugares = lugaresFiltrados,
                seleccionado = seleccionado,
                masCercanoId = masCercano?.id,
                radioMetros = radioMetros,
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { alPulsar() },
                    enabled = estado !is UbicacionUi.Buscando
                ) {
                    Text(if (estado is UbicacionUi.Buscando) "Buscando..." else "Obtener mi ubicación")
                }
                when (val e = estado) {
                    is UbicacionUi.Lista -> Text(
                        "Lat: %.5f   Lon: %.5f".format(e.posicion.latitud, e.posicion.longitud)
                    )
                    is UbicacionUi.Error -> MensajeError(error = e, onReintentar = { alPulsar() })
                    UbicacionUi.Buscando -> CircularProgressIndicator()
                    UbicacionUi.Inicial -> Text("Pulsa el botón para buscar tu ubicación")
                }
                OutlinedButton(
                    onClick = { masCercano?.let { vm.seleccionar(it.id) } },
                    enabled = masCercano != null
                ) {
                    Text("Ir al más cercano")
                }
                masCercano?.let { lugar ->
                    val metros = todosLosLugares.firstOrNull { it.lugar.id == lugar.id }?.metros
                    Text("Más cercano: ${lugar.nombre} (${metros?.let { formatearDistancia(it) } ?: ""})")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Radio:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                val opcionesRadio = listOf(100, 200, 500)
                opcionesRadio.forEach { m ->
                    FilterChip(
                        selected = radioMetros == m,
                        onClick = { vm.cambiarRadio(m) },
                        label = { Text("$m m") }
                    )
                }
            }
            val categorias = listOf("Todas", "Estudio", "Comida", "Eventos", "Servicios")
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { cat ->
                    val esTodas = cat == "Todas"
                    val estaSeleccionado = if (esTodas) categoriaSeleccionada == null else categoriaSeleccionada == cat
                    FilterChip(
                        selected = estaSeleccionado,
                        onClick = {
                            if (esTodas) {
                                vm.filtrar(null)
                            } else {
                                vm.filtrar(if (estaSeleccionado) null else cat)
                            }
                        },
                        label = { Text(cat) }
                    )
                }
            }
            ListaLugares(
                lugares = lugaresFiltrados,
                seleccionado = seleccionado,
                onClick = { vm.seleccionar(it) },
                masCercanoId = masCercano?.id,
                modifier = Modifier.fillMaxWidth().height(220.dp)
            )
        }
    }
}
