package com.diegovalberdi.androidexercise.data.remote

import com.diegovalberdi.androidexercise.model.User

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface UserApi {
    @GET("api/User")
    suspend fun getAll(): Response<List<User>>

    @GET("api/User/{id}")
    suspend fun getOne(@Path("id") userId: Int): Response<User>

    @POST("api/User")
    suspend fun postOne(@Body user: User): Response<Void>

    @PUT("api/User")
    suspend fun putOne(@Body user: User): Response<Void>

    @DELETE("api/User/{id}")
    suspend fun deleteOne(@Path("id") userId: Int): Response<Void>
}

object RetrofitFactory {
    const val BASE_URL = "http://hello-world.innocv.com/"

    fun getUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(UserApi::class.java)
    }
}