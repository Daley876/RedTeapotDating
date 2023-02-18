package com.example.redteapotdating.network

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import retrofit2.Call

class ServiceConnection (private val apiClient : MyApiServices) {
   suspend fun getUsersApiCall() : Call<UsersInfo> {
       return apiClient.getAllUsers()
    }

   suspend fun getProfileApiCall() : Call<ProfileConfig> {
        return apiClient.getConfig()
    }
}