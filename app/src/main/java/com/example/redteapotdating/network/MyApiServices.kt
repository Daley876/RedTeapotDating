package com.example.redteapotdating.network

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import retrofit2.Call
import retrofit2.http.GET

interface MyApiServices {

    @GET("users")
    fun getAllUsers() : Call<UsersInfo>

    @GET("config")
    fun getConfig() : Call <ProfileConfig>
}