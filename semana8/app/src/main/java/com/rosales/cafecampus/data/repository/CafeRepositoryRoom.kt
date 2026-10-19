package com.rosales.cafecampus.data.repository

import com.rosales.cafecampus.data.local.CafeDatabase
import com.rosales.cafecampus.data.local.entity.DetallePedido
import com.rosales.cafecampus.data.local.entity.Pedido
import com.rosales.cafecampus.data.local.entity.Producto
import com.rosales.cafecampus.data.local.entity.Usuario
import com.rosales.cafecampus.data.local.relation.PedidoConDetalle
import com.rosales.cafecampus.data.local.relation.PedidosPorHora
import com.rosales.cafecampus.model.EstadoPedido
import com.rosales.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import com.rosales.cafecampus.model.Rol
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
class CafeRepositoryRoom(db: CafeDatabase, private val context: Context) : CafeRepository {

    private val productoDao = db.productoDao()
    private val usuarioDao = db.usuarioDao()
    private val pedidoDao = db.pedidoDao()
    override suspend fun login(correo: String, clave: String): Usuario? =
        usuarioDao.login(correo.trim(), clave)

    override suspend fun registrarEstudiante(nombre: String, correo: String, clave: String): Boolean {
        val correoLimpio = correo.trim()
        if (usuarioDao.existeCorreo(correoLimpio)) return false
        val nuevoUsuario = Usuario(
            nombre = nombre.trim(),
            correo = correoLimpio,
            clave = clave,
            rol = Rol.CLIENTE.name
        )
        usuarioDao.insertar(nuevoUsuario)
        return true
    }
    override suspend fun obtenerUsuario(id: Int): Usuario? = usuarioDao.obtener(id)

    override suspend fun usuarioDemo(rol: Rol): Usuario? = usuarioDao.primeroPorRol(rol.name)
    override fun observarProductos(): Flow<List<Producto>> = productoDao.observarTodos()

    override suspend fun guardarProducto(p: Producto) {
        if (p.id == 0) productoDao.insertar(p) else productoDao.actualizar(p)
    }

    override suspend fun guardarImagen(uri: String): String? = withContext(Dispatchers.IO) {
        try {
            val carpeta = File(context.filesDir, "productos").apply { mkdirs() }
            val destino = File(carpeta, "prod_${System.currentTimeMillis()}.jpg")
            val entrada = context.contentResolver.openInputStream(Uri.parse(uri))
                ?: return@withContext null
            entrada.use { e -> destino.outputStream().use { s -> e.copyTo(s) } }
            destino.absolutePath
        } catch (e: Exception) {
            null
        }
    }
    override suspend fun eliminarProducto(p: Producto) = productoDao.eliminar(p)

    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) =
        productoDao.cambiarDisponibilidad(id, disponible)
    override suspend fun crearPedido(
        clienteId: Int, items: List<ItemCarrito>,
        horaRecojo: String, pago: String, nota: String
    ): Long {
        val detalles = items.map {
            DetallePedido(
                productoId = it.producto.id,
                nombreProducto = it.producto.nombre,
                cantidad = it.cantidad,
                precioUnitario = it.producto.precio
            )
        }
        val total = items.sumOf { it.subtotal }
        val pedido = Pedido(
            clienteId = clienteId,
            horaRecojo = horaRecojo,
            pago = pago,
            nota = nota,
            total = total
        )
        return pedidoDao.crearPedido(pedido, detalles)
    }

    override fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>> =
        pedidoDao.observarPorCliente(clienteId)

    override fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>> =
        pedidoDao.observarPorEstado(estado.name)

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> =
        pedidoDao.observarUno(id)

    override suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido) =
        pedidoDao.cambiarEstado(pedidoId, estado.name)
    override fun observarVentasHoy(inicioDia: Long): Flow<Double> =
        pedidoDao.ventasDesde(inicioDia)

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> =
        pedidoDao.cantidadPorEstado(estado.name)

    override fun observarPedidosPorHora(inicioDia: Long): Flow<List<PedidosPorHora>> =
        pedidoDao.observarPedidosPorHora(inicioDia)

    override fun observarPedidosHoy(inicioDia: Long): Flow<Int> =
        pedidoDao.observarPedidosHoy(inicioDia)
}