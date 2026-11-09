package com.tecsup.auramakeup.ui.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.repository.CarritoRepository
import com.tecsup.auramakeup.data.repository.ItemRepository
import com.tecsup.auramakeup.domain.model.Item
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetalleViewModel(
    private val repo: ItemRepository,
    private val carrito: CarritoRepository,
    private val id: Int
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<Item>>(UiState.Cargando)
    val estado: StateFlow<UiState<Item>> = _estado.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            repo.obtenerDetalle(id)
                .onSuccess { _estado.value = UiState.Exito(it) }
                .onFailure { _estado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun alternarFavorito() {
        viewModelScope.launch {
            repo.alternarFavorito(id)
            val actual = _estado.value
            if (actual is UiState.Exito) {
                _estado.value = UiState.Exito(actual.datos.copy(esFavorito = !actual.datos.esFavorito))
            }
        }
    }

    fun agregarAlCarrito() {
        val actual = _estado.value
        if (actual !is UiState.Exito) return
        viewModelScope.launch {
            carrito.agregar(actual.datos)
            _mensaje.value = "Agregado al carrito"
        }
    }

    fun mensajeMostrado() {
        _mensaje.value = null
    }

    private fun mensajeDe(e: Throwable) = when (e) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${e.code()}"
        else -> "Ocurrió un error inesperado"
    }

    companion object {
        fun factory(container: AppContainer, id: Int) = viewModelFactory {
            initializer { DetalleViewModel(container.itemRepository, container.carritoRepository, id) }
        }
    }
}
