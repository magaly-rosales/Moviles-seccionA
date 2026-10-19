package com.rosales.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.data.local.relation.PedidosPorHora
import com.rosales.cafecampus.data.repository.CafeRepository
import com.rosales.cafecampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class PanelAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    val nuevos: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val preparando: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val listos: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val ventasHoy: StateFlow<Double> = repo.observarVentasHoy(inicioDelDia())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val pedidosHoy: StateFlow<Int> = repo.observarPedidosHoy(inicioDelDia())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val pedidosPorHora: StateFlow<List<PedidosPorHora>> = repo.observarPedidosPorHora(inicioDelDia())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pedidosNuevos: StateFlow<List<PedidoConDetalle>> =
        repo.observarPedidosPorEstado(EstadoPedido.RECIBIDO)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun preparar(pc: PedidoConDetalle) {
        viewModelScope.launch { repo.cambiarEstado(pc.pedido.id, EstadoPedido.PREPARANDO) }
    }

    private fun inicioDelDia(): Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}