package com.tecsup.auramakeup.data.perfil

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PerfilRepositoryAndroid(
    private val context: Context
) : PerfilRepository {

    private val prefs = context.getSharedPreferences("aura_perfil", Context.MODE_PRIVATE)
    private val fotosFlows = mutableMapOf<String, MutableStateFlow<String?>>()

    private fun obtenerRutaConTimestamp(uid: String): String? {
        val archivo = File(context.filesDir, "perfil_$uid.jpg")
        if (archivo.exists()) {
            return "${archivo.absolutePath}?t=${archivo.lastModified()}"
        }
        val rutaGuardada = prefs.getString("foto_$uid", null) ?: return null
        val archivoGuardado = File(rutaGuardada)
        return if (archivoGuardado.exists()) {
            "${archivoGuardado.absolutePath}?t=${archivoGuardado.lastModified()}"
        } else {
            null
        }
    }

    private fun getFlow(uid: String): MutableStateFlow<String?> {
        return fotosFlows.getOrPut(uid) {
            MutableStateFlow(obtenerRutaConTimestamp(uid))
        }
    }

    override fun foto(uid: String): Flow<String?> = getFlow(uid).asStateFlow()

    override suspend fun guardarFoto(uid: String, uriString: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val uri = Uri.parse(uriString)
            val destino = File(context.filesDir, "perfil_$uid.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destino).use { output ->
                    input.copyTo(output)
                }
            }
            val ruta = destino.absolutePath
            prefs.edit().putString("foto_$uid", ruta).apply()
            getFlow(uid).value = "$ruta?t=${destino.lastModified()}"
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
