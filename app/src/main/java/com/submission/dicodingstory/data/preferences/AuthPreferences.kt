package com.submission.dicodingstory.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.submission.dicodingstory.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val userToken = stringPreferencesKey(Constants.userTokenKey)
    private val userName = stringPreferencesKey(Constants.userNameKey)

    fun getUserData(): Flow<UserData> {
        return dataStore.data.map { preferences ->
            val token = preferences[userToken] ?: ""
            val name = preferences[userName] ?: ""
            UserData(token, name)
        }
    }

    suspend fun saveUserData(userData: UserData) {
        dataStore.edit { preferences ->
            preferences[userToken] = userData.token!!
            preferences[userName] = userData.name!!
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}