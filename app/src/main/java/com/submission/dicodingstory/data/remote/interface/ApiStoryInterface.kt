package com.submission.dicodingstory.data.remote.`interface`

import com.submission.dicodingstory.data.remote.body.LoginBody
import com.submission.dicodingstory.data.remote.body.RegisterBody
import com.submission.dicodingstory.data.remote.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiStoryInterface {
    @POST("register")
    @Headers("Content-Type: application/json")
    fun register(
        @Body registerRequest: RegisterBody
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body loginRequest: LoginBody
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    @Headers("Content-Type: multipart/form-data")
    fun addStory(
        @Header("Authorization") token: String,
        @Part("description") content: String,
        @Part("lat") lat: String,
        @Part("lon") lon: String,
        @Part photo: MultipartBody.Part
    ): Call<PostStoryResponse>

    @Multipart
    @POST("/stories/guest")
    @Headers("Content-Type: multipart/form-data")
    fun addStoryGuest(
        @Part("description") content: String,
        @Part("lat") lat: String,
        @Part("lon") lon: String,
        @Part photo: MultipartBody.Part
    ): Call<PostStoryResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int?,
        @Query("size") limit: Int?,
        @Query("location") location: Boolean?
    ): Call<GetStoryResponse>

    @GET("/stories/:id")
    fun getDetailStory(
        @Header("Authorization") token: String, @Path("id") id: String
    ): Call<GetDetailStoryResponse>
}