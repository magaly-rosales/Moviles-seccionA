package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MenuViewModel(private val repo: CafeRepository) : ViewModel() {

    val productos: StateFlow<List<Producto>> = repo.observarProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun productoPorId(id: Int): Flow<Producto?> =
        repo.observarProductos().map { lista -> lista.find { it.id == id } }

    // ---------- Carrito ----------
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    val cantidadEnCarrito: StateFlow<Int> = _carrito
        .map { lista -> lista.sumOf { it.cantidad } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    fun agregar(producto: Producto, cantidad: Int, nota: String) {
        if (!producto.disponible || cantidad < 1) return
        _carrito.update { lista ->
            val yaEsta = lista.any { it.producto.id == producto.id }
            if (yaEsta) {

                lista.map {
                    if (it.producto.id == producto.id)
                        it.copy(cantidad = it.cantidad + cantidad, nota = nota.ifBlank { it.nota })
                    else it
                }
            } else {
                lista + ItemCarrito(producto, cantidad, nota)
            }
        }
    }

    fun quitar(productoId: Int) {
        _carrito.update { lista -> lista.filterNot { it.producto.id == productoId } }
    }

    fun vaciar() {
        _carrito.value = emptyList()
    }

    fun total(): Double = _carrito.value.sumOf { it.subtotal }
}