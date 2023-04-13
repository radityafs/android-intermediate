package com.submission.dicodingstory.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class Constants {
    companion object {
        const val userNameKey = "user_name"
        const val userTokenKey = "user_token"
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")
    }
}