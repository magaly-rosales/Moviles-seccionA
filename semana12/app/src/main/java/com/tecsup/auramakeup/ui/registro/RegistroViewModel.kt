package com.tecsup.auramakeup.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.auth.AuthRepository
import com.tecsup.auramakeup.domain.model.Sesion
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val auth: AuthRepository
) : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo.asStateFlow()

    private val _clave = MutableStateFlow("")
    val clave: StateFlow<String> = _clave.asStateFlow()

    private val _confirmarClave = MutableStateFlow("")
    val confirmarClave: StateFlow<String> = _confirmarClave.asStateFlow()

    private val _estado = MutableStateFlow<UiState<Sesion>?>(null)
    val estado: StateFlow<UiState<Sesion>?> = _estado.asStateFlow()

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    fun onNombreChange(valor: String) {
        _nombre.value = valor
        if (_estado.value is UiState.Error) _estado.value = null
    }

    fun onCorreoChange(valor: String) {
        _correo.value = valor
        if (_estado.value is UiState.Error) _estado.value = null
    }

    fun onClaveChange(valor: String) {
        _clave.value = valor
        if (_estado.value is UiState.Error) _estado.value = null
    }

    fun onConfirmarClaveChange(valor: String) {
        _confirmarClave.value = valor
        if (_estado.value is UiState.Error) _estado.value = null
    }

    fun registrar() {
        val nombre = _nombre.value.trim()
        val correo = _correo.value.trim()
        val clave = _clave.value
        val confirmarClave = _confirmarClave.value

        if (nombre.isBlank() || correo.isBlank() || clave.isBlank() || confirmarClave.isBlank()) {
            _estado.value = UiState.Error("Ingresa todos los campos")
            return
        }
        if (!emailRegex.matches(correo)) {
            _estado.value = UiState.Error("El correo no tiene un formato válido")
            return
        }
        if (clave.length < 6) {
            _estado.value = UiState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }
        if (clave != confirmarClave) {
            _estado.value = UiState.Error("Las contraseñas no coinciden")
            return
        }
        if (_estado.value is UiState.Cargando) return

        viewModelScope.launch {
            _estado.value = UiState.Cargando
            auth.registrar(nombre, correo, clave)
                .onSuccess { _estado.value = UiState.Exito(it) }
                .onFailure { _estado.value = UiState.Error(it.message ?: "No se pudo crear la cuenta") }
        }
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { RegistroViewModel(container.authRepository) }
        }
    }
}
