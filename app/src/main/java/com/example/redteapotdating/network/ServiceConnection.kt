package com.example.redteapotdating.network

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import retrofit2.Response

class ServiceConnection (private val apiClient : ServicesInterface) {
    fun getUsersApiCall() : Response<UsersInfo> {
       return apiClient.getAllUsers()
    }

    fun getProfileApiCall() : Response<ProfileConfig> {
        return apiClient.getConfig()
    }
}