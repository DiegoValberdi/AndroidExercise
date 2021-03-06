package com.diegovalberdi.androidexercise.data.remote

import com.diegovalberdi.androidexercise.model.User

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface UserApi {
    @GET("User")
    suspend fun getAll(): Response<List<User>>

    @GET("User/{id}")
    suspend fun getOne(@Path("id") userId: Int): Response<User>

    @POST("User")
    suspend fun postOne(@Body user: User): Response<Void>

    @PUT("User")
    suspend fun putOne(@Body user: User): Response<Void>

    @DELETE("User/{id}")
    suspend fun deleteOne(@Path("id") userId: Int): Response<Void>
}

object RetrofitFactory {
    const val BASE_URL = "http://hello-world.innocv.com/api/"

    fun getUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(UserApi::class.java)
    }
}