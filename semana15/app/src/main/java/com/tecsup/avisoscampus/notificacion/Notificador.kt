package com.tecsup.avisoscampus.notificacion

import android.content.Context
import com.tecsup.avisoscampus.model.Aviso

object Notificador {
    const val CANAL = "avisos"

    // TODO Paso 1: Notificador.crearCanal(context)
    fun crearCanal(context: Context) {
    }

    // TODO Paso 2: Notificador.tienePermiso(context)
    fun tienePermiso(context: Context): Boolean {
        return true
    }

    // TODO Paso 3: Notificador.mostrar(context, aviso)
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        return false
    }
}
