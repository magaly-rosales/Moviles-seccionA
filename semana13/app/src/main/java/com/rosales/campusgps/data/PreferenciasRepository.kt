package com.rosales.campusgps.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferencias_campus")

interface PreferenciasRepository {
    suspend fun guardarUltimoLugar(id: Int)
    val ultimoLugar: Flow<Int?>
}

class PreferenciasRepositoryDataStore(
    private val context: Context
) : PreferenciasRepository {

    private object PreferencesKeys {
        val ULTIMO_LUGAR_ID = intPreferencesKey("ultimo_lugar_id")
    }

    override suspend fun guardarUltimoLugar(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ULTIMO_LUGAR_ID] = id
        }
    }

    override val ultimoLugar: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ULTIMO_LUGAR_ID]
    }
}
