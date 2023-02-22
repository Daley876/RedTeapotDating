package com.example.redteapotdating.network.interfaces

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import retrofit2.Call
import retrofit2.http.GET

interface ProfileOrderServicesApi {

    @GET("/users")
    fun getAllUsers() : Call<UsersInfo>

    @GET("/config")
    fun getConfig() : Call <ProfileConfig>
}