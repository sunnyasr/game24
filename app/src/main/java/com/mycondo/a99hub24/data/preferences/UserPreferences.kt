package com.mycondo.a99hub24.data.preferences

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.responses.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> =
        applicationContext.createDataStore(
            name = "login_pref"
        )

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val username: Flow<String?>
        get() = dataStore.data.map { preferences ->
                preferences[KEY_USERNAME] ?: ""
            }

    suspend fun saveAuthToken(authToken: LoginResponse) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken.token
            preferences[KEY_CLIENT_ID] = authToken.client_id
            preferences[KEY_STATUS] = authToken.status
            preferences[KEY_USERNAME] = authToken.username
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        val KEY_CLIENT_ID = preferencesKey<String>("key_client_id")
        val KEY_STATUS = preferencesKey<Boolean>("key_status")
        val KEY_USERNAME = preferencesKey<String>("key_username")
    }

}