package com.rosales.cafecampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.model.Rol
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.CafeTextField
import com.rosales.cafecampus.ui.theme.CafeTheme
import com.rosales.cafecampus.viewmodel.SesionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    vm: SesionViewModel,
    onIngresar: (Rol) -> Unit,
    onRegistro: () -> Unit = {}
) {
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    val mensajeError by vm.error.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()
    CafeTheme(rol = rol, oscuro = oscuro) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(32.dp))
                Icon(
                    Icons.Filled.LocalCafe, null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                Text("CafeCampus", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Inicia sesión para continuar", color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(Modifier.height(24.dp))

                val opciones = listOf(Rol.CLIENTE to "Estudiante", Rol.ADMIN to "Administrador")
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                    opciones.forEachIndexed { i, (r, etiqueta) ->
                        SegmentedButton(
                            selected = rol == r,
                            onClick = { rol = r; vm.limpiarError() },
                            shape = SegmentedButtonDefaults.itemShape(i, opciones.size)
                        ) { Text(etiqueta) }
                    }
                }

                Spacer(Modifier.height(20.dp))
                CafeTextField(
                    valor = correo,
                    onValor = { correo = it; vm.limpiarError() },
                    etiqueta = "Correo",
                    teclado = KeyboardType.Email
                )
                Spacer(Modifier.height(12.dp))
                CafeTextField(
                    valor = clave,
                    onValor = { clave = it; vm.limpiarError() },
                    etiqueta = "Contraseña",
                    esClave = true
                )

                mensajeError?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(20.dp))
                CafeButton("Ingresar", onClick = { vm.login(correo, clave, rol, onIngresar) })

                if (rol == Rol.CLIENTE) {
                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = onRegistro) {
                        Text("¿No tienes cuenta? Regístrate")
                    }
                }
            }
        }
    }
}