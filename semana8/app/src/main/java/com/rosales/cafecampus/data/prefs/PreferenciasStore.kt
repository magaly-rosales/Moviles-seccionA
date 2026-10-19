package com.rosales.cafecampus.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rosales.cafecampus.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ajustes")

class PreferenciasStore(private val context: Context) {

    private object Claves {
        val PERFIL = stringPreferencesKey("perfilActivo")
        val OSCURO = booleanPreferencesKey("modoOscuro")
        val RECIBIR = booleanPreferencesKey("recibirPedidos")
        val USUARIO_ID = intPreferencesKey("usuarioId")
    }

    val perfilActivo: Flow<String> = context.dataStore.data.map { it[Claves.PERFIL] ?: "" }
    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { it[Claves.OSCURO] ?: false }
    val recibirPedidos: Flow<Boolean> = context.dataStore.data.map { it[Claves.RECIBIR] ?: true }
    val usuarioId: Flow<Int> = context.dataStore.data.map { it[Claves.USUARIO_ID] ?: 0 }
    suspend fun guardarPerfil(rol: Rol) {
        context.dataStore.edit { it[Claves.PERFIL] = rol.name }
    }

    suspend fun guardarUsuarioId(id: Int) {
        context.dataStore.edit { it[Claves.USUARIO_ID] = id }
    }

    suspend fun guardarModoOscuro(b: Boolean) {
        context.dataStore.edit { it[Claves.OSCURO] = b }
    }

    suspend fun guardarRecibirPedidos(b: Boolean) {
        context.dataStore.edit { it[Claves.RECIBIR] = b }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit {
            it.remove(Claves.PERFIL)
            it.remove(Claves.USUARIO_ID)
        }
    }
}