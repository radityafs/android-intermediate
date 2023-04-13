package com.submission.dicodingstory.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.dicodingstory.data.remote.body.LoginBody
import com.submission.dicodingstory.data.remote.body.RegisterBody
import com.submission.dicodingstory.data.remote.response.*
import com.submission.dicodingstory.data.repository.ApiStoryRepository
import com.submission.dicodingstory.utils.Resource
import kotlinx.coroutines.launch

class ApiStoryViewModel(private val userRepository: ApiStoryRepository) : ViewModel() {

    var registerData: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    var loginData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    var storyData: MutableLiveData<Resource<GetStoryResponse>> = MutableLiveData()

    fun register(body: RegisterBody) = viewModelScope.launch {
        registerData.postValue(Resource.Loading())
        try {
            val response = userRepository.register(body)

            if (response?.isSuccessful == true) {
                registerData.postValue(Resource.Success(response?.body()!!))
            } else {
                registerData.postValue(Resource.Error(response?.errorBody().toString()))
            }
        } catch (e: Exception) {
            registerData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun login(body: LoginBody) = viewModelScope.launch {
        registerData.postValue(Resource.Loading())
        try {
            val response = userRepository.login(body)

            if (response?.isSuccessful == true) {
                loginData.postValue(Resource.Success(response?.body()!!))
            } else {
                loginData.postValue(Resource.Error(response?.errorBody().toString()))
            }
        } catch (e: Exception) {
            registerData.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getStory(token: String) = viewModelScope.launch {
        registerData.postValue(Resource.Loading())
        try {
            val response = userRepository.getStories(token, 1, 10, true)

            if (response?.isSuccessful == true) {
                storyData.postValue(Resource.Success(response?.body()!!))
            } else {
                storyData.postValue(Resource.Error(response?.errorBody().toString()))
            }
        } catch (e: Exception) {
            registerData.postValue(Resource.Error(e.message.toString()))
        }
    }
}
