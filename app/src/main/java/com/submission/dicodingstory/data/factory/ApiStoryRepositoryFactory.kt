package com.submission.dicodingstory.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.dicodingstory.data.di.ApiStoryRepositoryInject
import com.submission.dicodingstory.data.repository.ApiStoryRepository
import com.submission.dicodingstory.viewmodel.ApiStoryViewModel

class ApiStoryRepositoryFactory private constructor(private val apiStoryRepository: ApiStoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiStoryViewModel::class.java)) {
            return ApiStoryViewModel(apiStoryRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ApiStoryRepositoryFactory? = null
        fun getInstance(context: Context): ApiStoryRepositoryFactory =
            instance ?: synchronized(this) {
                instance ?: ApiStoryRepositoryFactory(
                    ApiStoryRepositoryInject.provideRepository(
                        context
                    )
                )
            }.also { instance = it }
    }
}