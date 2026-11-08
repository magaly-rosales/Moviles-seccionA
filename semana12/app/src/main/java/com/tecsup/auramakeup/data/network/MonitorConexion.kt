package com.tecsup.auramakeup.data.network

import kotlinx.coroutines.flow.Flow

interface MonitorConexion {
    val conectado: Flow<Boolean>
}