package com.tecsup.auramakeup.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.auth.AuthRepository
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.notification.Notificador
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ResumenAdmin(
    val totalGuardados: Int,
    val totalFavoritos: Int
)

class AdminViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador,
    private val auth: AuthRepository? = null
) : ViewModel() {

    val estado: StateFlow<UiState<ResumenAdmin>> =
        combine(repo.items, repo.favoritos) { items, favoritos ->
            UiState.Exito(ResumenAdmin(items.size, favoritos.size)) as UiState<ResumenAdmin>
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)

    private val _ocupado = MutableStateFlow(false)
    val ocupado: StateFlow<Boolean> = _ocupado.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _correoAdmin = MutableStateFlow("")
    val correoAdmin: StateFlow<String> = _correoAdmin.asStateFlow()

    init {
        auth?.let {
            viewModelScope.launch {
                _correoAdmin.value = it.sesionActual()?.correo ?: ""
            }
        }
    }

    fun sincronizar() {
        if (_ocupado.value) return
        viewModelScope.launch {
            _ocupado.value = true
            repo.refrescar()
                .onSuccess { n ->
                    _mensaje.value = "Sincronizado: se guardaron $n productos"
                    notificador.mostrar("Aura Makeup", "Se guardaron $n productos")
                }
                .onFailure { _mensaje.value = "No se pudo sincronizar: revisa tu conexión" }
            _ocupado.value = false
        }
    }

    fun limpiarCache() {
        viewModelScope.launch {
            repo.limpiarCache()
            _mensaje.value = "Caché limpiada"
        }
    }

    fun mensajeMostrado() {
        _mensaje.value = null
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { AdminViewModel(container.itemRepository, container.notificador, container.authRepository) }
        }
    }
}
