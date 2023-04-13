package com.submission.dicodingstory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submission.dicodingstory.data.preferences.AuthPreferences
import com.submission.dicodingstory.data.preferences.UserData
import kotlinx.coroutines.launch

class AuthPreferencesViewModel(private val pref: AuthPreferences) : ViewModel() {
    fun getUserData(): LiveData<UserData> {
        return pref.getUserData().asLiveData()
    }

    fun saveUserData(userData: UserData) {
        viewModelScope.launch {
            pref.saveUserData(userData)
        }
    }
}