package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.data.prefs.PreferenciasStore
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.model.ItemCarrito
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PedidosClienteViewModel(
    private val repo: CafeRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val misPedidos: StateFlow<List<PedidoConDetalle>> = prefs.usuarioId
        .flatMapLatest { id -> repo.observarPedidosCliente(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val estadosActivos = setOf(
        EstadoPedido.RECIBIDO.name, EstadoPedido.PREPARANDO.name, EstadoPedido.LISTO.name
    )

    val activos: StateFlow<List<PedidoConDetalle>> = misPedidos
        .map { lista -> lista.filter { it.pedido.estado in estadosActivos } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historial: StateFlow<List<PedidoConDetalle>> = misPedidos
        .map { lista -> lista.filterNot { it.pedido.estado in estadosActivos } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun pedido(id: Int): StateFlow<PedidoConDetalle?> =
        repo.observarPedido(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun confirmar(
        items: List<ItemCarrito>,
        horaRecojo: String,
        pago: String,
        nota: String,
        onCreado: (Int) -> Unit = {}
    ) {
        if (items.isEmpty()) return
        viewModelScope.launch {
            val clienteId = prefs.usuarioId.first()
            val id = repo.crearPedido(clienteId, items, horaRecojo, pago, nota)
            onCreado(id.toInt())
        }
    }
}