package com.tecsup.auramakeup.ui.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tecsup.auramakeup.AppContainer
import com.tecsup.auramakeup.data.repository.CarritoRepository
import com.tecsup.auramakeup.domain.model.ItemCarrito
import com.tecsup.auramakeup.ui.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val repo: CarritoRepository
) : ViewModel() {

    val estado: StateFlow<UiState<List<ItemCarrito>>> = repo.items
        .map<List<ItemCarrito>, UiState<List<ItemCarrito>>> { UiState.Exito(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)

    val total: StateFlow<Double> = repo.items
        .map { lista -> lista.sumOf { it.subtotal } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    val cantidad: StateFlow<Int> = repo.items
        .map { lista -> lista.sumOf { it.cantidad } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun aumentar(id: Int) {
        viewModelScope.launch { repo.aumentar(id) }
    }

    fun disminuir(id: Int) {
        viewModelScope.launch { repo.disminuir(id) }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch { repo.eliminar(id) }
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer { CarritoViewModel(container.carritoRepository) }
        }
    }
}