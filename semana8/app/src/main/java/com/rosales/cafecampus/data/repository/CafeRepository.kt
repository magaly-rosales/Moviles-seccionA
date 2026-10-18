package com.rosales.cafecampus.data.repository

import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.local.entity.Usuario
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import com.rosales.cafecampus.model.Rol
interface CafeRepository {
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun obtenerUsuario(id: Int): Usuario?
    suspend fun usuarioDemo(rol: Rol): Usuario?
    fun observarProductos(): Flow<List<Producto>>
    suspend fun guardarProducto(p: Producto)
    suspend fun eliminarProducto(p: Producto)
    suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean)

    suspend fun crearPedido(
        clienteId: Int, items: List<ItemCarrito>,
        horaRecojo: String, pago: String, nota: String
    ): Long
    fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido)

    fun observarVentasHoy(inicioDia: Long): Flow<Double>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
}