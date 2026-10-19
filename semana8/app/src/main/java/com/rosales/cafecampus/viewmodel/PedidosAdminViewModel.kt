package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.model.EstadoPedido
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PedidosAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    val estadoSeleccionado = MutableStateFlow(EstadoPedido.RECIBIDO)


    @OptIn(ExperimentalCoroutinesApi::class)
    val pedidos: StateFlow<List<PedidoConDetalle>> = estadoSeleccionado
        .flatMapLatest { estado -> repo.observarPedidosPorEstado(estado) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seleccionar(estado: EstadoPedido) {
        estadoSeleccionado.value = estado
    }


    fun avanzar(pc: PedidoConDetalle) {
        val siguiente = EstadoPedido.valueOf(pc.pedido.estado).siguiente() ?: return
        viewModelScope.launch { repo.cambiarEstado(pc.pedido.id, siguiente) }
    }

    fun cancelar(pc: PedidoConDetalle) {
        viewModelScope.launch { repo.cambiarEstado(pc.pedido.id, EstadoPedido.CANCELADO) }
    }
    fun pedido(id: Int): StateFlow<PedidoConDetalle?> =
        repo.observarPedido(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun cambiarA(pc: PedidoConDetalle, estado: EstadoPedido) {
        viewModelScope.launch { repo.cambiarEstado(pc.pedido.id, estado) }
    }
}