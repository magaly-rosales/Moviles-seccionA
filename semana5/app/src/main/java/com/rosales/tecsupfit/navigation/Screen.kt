package com.rosales.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object Reservas : Screen("reservas")
    object Rutinas : Screen("rutinas")
    object Perfil : Screen("perfil")
    object Confirmacion : Screen("confirmacion/{claseId}") {
        fun crearRuta(claseId: Int) = "confirmacion/$claseId"
    }

    object Detalle : Screen("detalle/{claseId}") {
        fun crearRuta(claseId: Int) = "detalle/$claseId"
    }
}