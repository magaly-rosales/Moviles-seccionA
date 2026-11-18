package com.rosales.campusgps.data

import com.rosales.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}