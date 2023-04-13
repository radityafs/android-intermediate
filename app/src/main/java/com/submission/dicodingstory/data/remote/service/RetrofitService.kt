package com.submission.dicodingstory.data.remote.service

import com.submission.dicodingstory.BuildConfig
import com.submission.dicodingstory.BuildConfig.BASE_URL
import com.submission.dicodingstory.data.remote.`interface`.ApiStoryInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: ApiStoryInterface by lazy {
            retrofit.create(ApiStoryInterface::class.java)
        }
        
    }
}