package com.submission.dicodingstory.data.di

import android.content.Context
import com.submission.dicodingstory.data.remote.`interface`.ApiStoryInterface
import com.submission.dicodingstory.data.remote.service.RetrofitService
import com.submission.dicodingstory.data.repository.ApiStoryRepository

object ApiStoryRepositoryInject {
    fun provideRepository(context: Context): ApiStoryRepository {
        return ApiStoryRepository.getInstance()
    }
}