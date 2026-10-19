package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.repository.CafeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    val productos: StateFlow<List<Producto>> = repo.observarProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun producto(id: Int): Flow<Producto?> =
        repo.observarProductos().map { lista -> lista.find { it.id == id } }

    fun guardar(producto: Producto) {
        viewModelScope.launch { repo.guardarProducto(producto) }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch { repo.eliminarProducto(producto) }
    }

    fun alternarDisponibilidad(producto: Producto) {
        viewModelScope.launch { repo.cambiarDisponibilidad(producto.id, !producto.disponible) }
    }

    fun copiarImagen(uri: String, onListo: (String?) -> Unit) {
        viewModelScope.launch { onListo(repo.guardarImagen(uri)) }
    }
    fun guardarFormulario(
        id: Int?, nombre: String, categoria: String, precioTexto: String,
        descripcion: String, disponible: Boolean, icono: String, imagen: String?
    ): String? {
        val precio = precioTexto.replace(',', '.').toDoubleOrNull()
        return when {
            nombre.isBlank() -> "El nombre es obligatorio"
            precio == null || precio <= 0.0 -> "El precio debe ser mayor que 0"
            else -> {
                guardar(
                    Producto(
                        id = id ?: 0,
                        nombre = nombre.trim(),
                        descripcion = descripcion.trim(),
                        categoria = categoria.trim().ifBlank { "General" },
                        precio = precio,
                        disponible = disponible,
                        icono = icono,
                        imagen = imagen
                    )
                )
                null
            }
        }

    }
}