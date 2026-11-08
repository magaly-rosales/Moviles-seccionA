package com.tecsup.auramakeup.ui.navigation

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

class SesionViewModel(
    private val auth: AuthRepository
) : ViewModel() {

    private val _sesion = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
    val sesion: StateFlow<UiState<Sesion?>> = _sesion.asStateFlow()

    init {
        viewModelScope.launch {
            _sesion.value = UiState.Exito(auth.sesionActual())
        }
    }

    fun establecer(sesion: Sesion) {
        _sesion.value = UiState.Exito(sesion)
    }

    fun cerrarSesion() {
        auth.cerrarSesion()
        _sesion.value = UiState.Exito(null)
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { SesionViewModel(container.authRepository) }
        }
    }
}