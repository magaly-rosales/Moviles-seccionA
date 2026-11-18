package com.rosales.campusgps.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosales.campusgps.model.UbicacionUi
import com.rosales.campusgps.viewmodel.MapaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()

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
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { vm.buscar() },
                enabled = estado !is UbicacionUi.Buscando
            ) {
                Text("Obtener mi ubicación")
            }
            Spacer(Modifier.height(12.dp))
            when (val e = estado) {
                is UbicacionUi.Lista -> Text(
                    "Lat: %.5f   Lon: %.5f".format(e.posicion.latitud, e.posicion.longitud)
                )
                is UbicacionUi.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
                UbicacionUi.Buscando -> CircularProgressIndicator()
                UbicacionUi.Inicial -> Text("Pulsa el botón para buscar tu ubicación")
            }
        }
    }
}