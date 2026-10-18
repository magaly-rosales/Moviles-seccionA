package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.entity.Usuario
import com.rosales.cafecampus.data.prefs.PreferenciasStore
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: CafeRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val modoOscuro: StateFlow<Boolean> =
        prefs.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val recibirPedidos: StateFlow<Boolean> =
        prefs.recibirPedidos.stateIn(viewModelScope, SharingStarted.Eagerly, true)


    val perfilActivo: StateFlow<String?> =
        prefs.perfilActivo.stateIn<String?>(viewModelScope, SharingStarted.Eagerly, null)

    val usuarioId: StateFlow<Int> =
        prefs.usuarioId.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    init {

        viewModelScope.launch {
            val id = prefs.usuarioId.first()
            if (id != 0) _usuarioActual.value = repo.obtenerUsuario(id)
        }
    }

    fun limpiarError() { _error.value = null }

    fun login(correo: String, clave: String, rolSeleccionado: Rol, onExito: (Rol) -> Unit) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Completa correo y contraseña"
            return
        }
        viewModelScope.launch {
            val u = repo.login(correo, clave)
            when {
                u == null -> _error.value = "Correo o contraseña incorrectos"
                u.rol != rolSeleccionado.name -> _error.value =
                    if (rolSeleccionado == Rol.ADMIN) "Esta cuenta no es de administrador"
                    else "Esta cuenta no es de estudiante"
                else -> {
                    _error.value = null
                    _usuarioActual.value = u
                    prefs.guardarPerfil(rolSeleccionado)
                    prefs.guardarUsuarioId(u.id)
                    onExito(rolSeleccionado)
                }
            }
        }
    }

    fun guardarModoOscuro(b: Boolean) {
        viewModelScope.launch { prefs.guardarModoOscuro(b) }
    }

    fun guardarRecibirPedidos(b: Boolean) {
        viewModelScope.launch { prefs.guardarRecibirPedidos(b) }
    }


    fun cambiarPerfil(onListo: (Rol) -> Unit) {
        viewModelScope.launch {
            val otro = if (perfilActivo.value == Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
            val u = repo.usuarioDemo(otro) ?: return@launch
            prefs.guardarPerfil(otro)
            prefs.guardarUsuarioId(u.id)
            _usuarioActual.value = u
            onListo(otro)
        }
    }

    fun cerrarSesion(onListo: () -> Unit) {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _usuarioActual.value = null
            onListo()
        }
    }
}