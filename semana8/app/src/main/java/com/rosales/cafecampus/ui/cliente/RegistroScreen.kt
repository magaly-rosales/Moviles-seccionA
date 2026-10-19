package com.rosales.cafecampus.ui.cliente

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rosales.cafecampus.ui.components.AppTopBar
import com.rosales.cafecampus.ui.components.CafeButton
import com.rosales.cafecampus.ui.components.CafeTextField
import com.rosales.cafecampus.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    vm: RegistroViewModel,
    onAtras: () -> Unit,
    onRegistroExitoso: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmarClave by rememberSaveable { mutableStateOf("") }

    val mensajeError by vm.error.collectAsState()
    val cargando by vm.cargando.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                titulo = "Registro de estudiante",
                onAtras = onAtras
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Crea tu cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Ingresa tus datos para registrarte",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            CafeTextField(
                valor = nombre,
                onValor = {
                    nombre = it
                    vm.limpiarError()
                },
                etiqueta = "Nombre completo"
            )

            Spacer(Modifier.height(12.dp))

            CafeTextField(
                valor = correo,
                onValor = {
                    correo = it
                    vm.limpiarError()
                },
                etiqueta = "Correo institucional / personal",
                teclado = KeyboardType.Email
            )

            Spacer(Modifier.height(12.dp))

            CafeTextField(
                valor = clave,
                onValor = {
                    clave = it
                    vm.limpiarError()
                },
                etiqueta = "Contraseña",
                esClave = true
            )

            Spacer(Modifier.height(12.dp))

            CafeTextField(
                valor = confirmarClave,
                onValor = {
                    confirmarClave = it
                    vm.limpiarError()
                },
                etiqueta = "Confirmar contraseña",
                esClave = true
            )

            mensajeError?.let { err ->
                Spacer(Modifier.height(12.dp))
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            CafeButton(
                texto = if (cargando) "Registrando..." else "Crear cuenta",
                enabled = !cargando,
                onClick = {
                    vm.registrar(
                        nombre = nombre,
                        correo = correo,
                        clave = clave,
                        confirmarClave = confirmarClave,
                        onExito = {
                            Toast.makeText(
                                context,
                                "¡Cuenta creada con éxito! Inicia sesión.",
                                Toast.LENGTH_SHORT
                            ).show()
                            onRegistroExitoso()
                        }
                    )
                }
            )
        }
    }
}
