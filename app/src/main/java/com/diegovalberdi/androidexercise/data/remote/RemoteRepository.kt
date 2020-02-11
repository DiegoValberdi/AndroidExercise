package com.diegovalberdi.androidexercise.data.remote

import com.diegovalberdi.androidexercise.model.User


interface RemoteRepository {

    suspend fun getAll(): List<User>?
    suspend fun putOne(user: User) : String
    suspend fun postOne(user : User): String
    suspend fun getOne(userId: Int) : User?
    suspend fun deleteOne(userId: Int): String
}