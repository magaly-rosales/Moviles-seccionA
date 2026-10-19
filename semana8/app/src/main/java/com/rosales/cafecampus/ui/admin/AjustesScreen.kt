package com.rosales.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.Rol
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(
    vm: SesionViewModel,
    esAdmin: Boolean,
    onPerfilCambiado: (Rol) -> Unit,
    onSesionCerrada: () -> Unit
) {
    val usuario by vm.usuarioActual.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()
    val recibir by vm.recibirPedidos.collectAsState()
    val cs = MaterialTheme.colorScheme

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            if (esAdmin) "Ajustes" else "Mi perfil",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.AccountCircle, null, tint = cs.primary, modifier = Modifier.size(48.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        usuario?.nombre ?: if (esAdmin) "Administrador" else "Estudiante",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(usuario?.correo ?: "", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                    Text(
                        if (esAdmin) "Perfil: Administrador" else "Perfil: Estudiante",
                        style = MaterialTheme.typography.labelMedium,
                        color = cs.primary
                    )
                }
            }
        }

        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column {
                FilaSwitch(
                    Icons.Filled.DarkMode, "Modo oscuro", "Cambia el tema de toda la app",
                    oscuro
                ) { vm.guardarModoOscuro(it) }

                if (esAdmin) {
                    HorizontalDivider()
                    FilaSwitch(
                        Icons.Filled.Storefront, "Recibir pedidos",
                        if (recibir) "La cafetería está abierta" else "Los estudiantes verán \"Cafetería cerrada\"",
                        recibir
                    ) { vm.guardarRecibirPedidos(it) }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        CafeButton(
            if (esAdmin) "Cambiar a perfil Estudiante" else "Cambiar a perfil Administrador",
            onClick = { vm.cambiarPerfil(onPerfilCambiado) }
        )
        CafeButton(
            "Cerrar sesión",
            onClick = { vm.cerrarSesion(onSesionCerrada) },
            secundario = true
        )
    }
}

@Composable
private fun FilaSwitch(
    icono: ImageVector,
    titulo: String,
    subtitulo: String,
    valor: Boolean,
    onCambio: (Boolean) -> Unit
) {
    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(titulo, fontWeight = FontWeight.SemiBold)
            Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = valor, onCheckedChange = onCambio)
    }
}