package com.rosales.cafecampus.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.repository.CafeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun registrar(
        nombre: String,
        correo: String,
        clave: String,
        confirmarClave: String,
        onExito: () -> Unit
    ) {
        val nombreLimpio = nombre.trim()
        val correoLimpio = correo.trim()

        if (nombreLimpio.isBlank() || correoLimpio.isBlank() || clave.isBlank() || confirmarClave.isBlank()) {
            _error.value = "Todos los campos son obligatorios"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correoLimpio).matches()) {
            _error.value = "Ingresa un correo con formato válido"
            return
        }

        if (clave.length < 4) {
            _error.value = "La contraseña debe tener al menos 4 caracteres"
            return
        }

        if (clave != confirmarClave) {
            _error.value = "Las contraseñas no coinciden"
            return
        }

        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            val exito = repo.registrarEstudiante(nombreLimpio, correoLimpio, clave)
            _cargando.value = false
            if (exito) {
                onExito()
            } else {
                _error.value = "Ese correo ya está registrado"
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }
}
