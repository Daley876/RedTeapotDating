package com.example.redteapotdating.network

import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import retrofit2.Response
import retrofit2.http.GET

interface ServicesInterface {

    @GET("/users")
    fun getAllUsers() : Response<UsersInfo>

    @GET("/config")
    fun getConfig() : Response<ProfileConfig>
}