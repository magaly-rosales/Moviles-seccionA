package com.tecsup.auramakeup.data.perfil

import kotlinx.coroutines.flow.Flow

interface PerfilRepository {
    fun foto(uid: String): Flow<String?>
    suspend fun guardarFoto(uid: String, uriString: String): Result<Unit>
}
