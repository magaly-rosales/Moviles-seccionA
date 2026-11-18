package com.rosales.campusgps.data

import com.rosales.campusgps.model.Posicion

class UbicacionNoDisponibleException : Exception("Activa el GPS e intenta de nuevo")

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
    fun playServicesDisponible(): Boolean
}
