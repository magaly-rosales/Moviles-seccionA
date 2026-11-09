package com.tecsup.auramakeup.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.auth.AuthRepository
import com.tecsup.auramakeup.data.perfil.PerfilRepository
import com.tecsup.auramakeup.domain.model.Sesion
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val auth: AuthRepository,
    private val perfilRepo: PerfilRepository
) : ViewModel() {

    private val _sesionState = MutableStateFlow<UiState<Sesion>>(UiState.Cargando)
    val sesionState: StateFlow<UiState<Sesion>> = _sesionState.asStateFlow()

    private val _fotoRuta = MutableStateFlow<String?>(null)
    val fotoRuta: StateFlow<String?> = _fotoRuta.asStateFlow()

    private var currentUid: String? = null

    init {
        cargarPerfil()
    }

    fun cargarPerfil() {
        viewModelScope.launch {
            _sesionState.value = UiState.Cargando
            val sesion = auth.sesionActual()
            if (sesion != null) {
                currentUid = sesion.uid
                _sesionState.value = UiState.Exito(sesion)
                perfilRepo.foto(sesion.uid).collect { ruta ->
                    _fotoRuta.value = ruta
                }
            } else {
                _sesionState.value = UiState.Error("No se encontró sesión activa")
            }
        }
    }

    fun guardarFoto(uriString: String) {
        val uid = currentUid ?: return
        viewModelScope.launch {
            perfilRepo.guardarFoto(uid, uriString)
        }
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { PerfilViewModel(container.authRepository, container.perfilRepository) }
        }
    }
}
