package com.diegovalberdi.androidexercise.data.remote

import android.accounts.NetworkErrorException
import android.util.Log
import com.diegovalberdi.androidexercise.model.User


class RetrofitRemoteRepository(val userApi: UserApi) : RemoteRepository {

    var errorMessage = "This app's creator apologize for the technical problems"

    override suspend fun getAll(): List<User>? {
        val getAllUsersResponse = userApi.getAll()
        when (getAllUsersResponse.isSuccessful) {
            true -> return getAllUsersResponse.body()!!
            false -> return null
        }
    }
    override suspend fun putOne(user: User): String {
        val updateOneUserResponse = userApi.putOne(user)
        when (updateOneUserResponse.isSuccessful) {
            true -> return "The user was successfully updated"
            false -> return errorMessage
        }
    }

    override suspend fun postOne(user: User): String {
        val insertOneUserResponse = userApi.postOne(user)
        when (insertOneUserResponse.isSuccessful) {
            true -> return "The user was successfully inserted"
            false -> return errorMessage
        }
    }

    override suspend fun getOne(userId: Int): User? {
        val getOneUserResponse = userApi.getOne(userId)
        return getOneUserResponse.body()
    }

    override suspend fun deleteOne(userId: Int): String {
        val deleteOneUserResponse = userApi.deleteOne(userId)
        when (deleteOneUserResponse.isSuccessful) {
            true -> return "The user was successfully deleted"
            false -> return errorMessage
        }
    }


}