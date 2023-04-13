package com.submission.dicodingstory.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.submission.dicodingstory.data.preferences.AuthPreferences
import com.submission.dicodingstory.viewmodel.AuthPreferencesViewModel

class AuthPreferencesFactory(private val pref: AuthPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AuthPreferencesViewModel::class.java)) {
            return AuthPreferencesViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}