package com.rosales.cafecampus.ui.navigation

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTRO = "registro"

    // Cliente
    const val MENU = "menu"
    const val DETALLE_PRODUCTO = "producto/{id}"
    fun detalleProducto(id: Int) = "producto/$id"
    const val CONFIRMAR = "confirmar"
    const val SEGUIMIENTO = "seguimiento/{id}"
    fun seguimiento(id: Int) = "seguimiento/$id"
    const val MIS_PEDIDOS = "mis_pedidos"
    const val PERFIL = "perfil"

    // Admin
    const val PANEL = "panel"
    const val PEDIDOS_ADMIN = "pedidos_admin"
    const val DETALLE_PEDIDO_ADMIN = "pedido_admin/{id}"
    fun detallePedidoAdmin(id: Int) = "pedido_admin/$id"
    const val MENU_ADMIN = "menu_admin"
    const val FORM_PRODUCTO = "form_producto?id={id}"
    fun formProducto(id: Int? = null) = if (id == null) "form_producto" else "form_producto?id=$id"
    const val AJUSTES = "ajustes"
}