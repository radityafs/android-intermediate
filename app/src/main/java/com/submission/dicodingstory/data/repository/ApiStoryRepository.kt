package com.submission.dicodingstory.data.repository

import com.submission.dicodingstory.data.remote.body.LoginBody
import com.submission.dicodingstory.data.remote.body.RegisterBody
import com.submission.dicodingstory.data.remote.`interface`.ApiStoryInterface
import com.submission.dicodingstory.data.remote.service.RetrofitService
import okhttp3.MultipartBody
import retrofit2.awaitResponse

class ApiStoryRepository private constructor() {

    suspend fun login(body: LoginBody) = RetrofitService.api?.login(body)?.awaitResponse()
    suspend fun register(body: RegisterBody) =
        RetrofitService.api?.register(body)?.awaitResponse()

    suspend fun addStory(
        token: String, content: String, lat: String, lon: String, photo: MultipartBody.Part
    ) = RetrofitService.api?.addStory(token, content, lat, lon, photo)

    suspend fun addStoryGuest(
        content: String, lat: String, lon: String, photo: MultipartBody.Part
    ) = RetrofitService.api?.addStoryGuest(content, lat, lon, photo)

    suspend fun getStories(token: String, page: Int?, limit: Int?, location: Boolean?) =
        RetrofitService.api?.getStories(token, page, limit, location)?.awaitResponse()

    suspend fun getDetailStory(token: String, id: String) =
        RetrofitService.api?.getDetailStory(token, id)

    companion object {
        @Volatile
        private var instance: ApiStoryRepository? = null

        fun getInstance(): ApiStoryRepository =
            instance ?: synchronized(this) {
                instance ?: ApiStoryRepository().apply { instance = this }
            }
    }
}