package com.example.redteapotdating.repository

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.network.NetworkConnection

class UsersRepository {
    private val networkConn = NetworkConnection

    fun getUsers() : UsersInfo?{
        val response = networkConn.connection.getUsersApiCall()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    fun getProfileConfig() : ProfileConfig?{
        val response = networkConn.connection.getProfileApiCall()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
}