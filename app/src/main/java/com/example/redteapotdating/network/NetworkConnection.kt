package com.example.redteapotdating.network

import com.example.redteapotdating.network.interfaces.NetworkConnServiceInterface
import com.example.redteapotdating.network.interfaces.ProfileOrderServicesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConnection : NetworkConnServiceInterface {
    private const val BASE_URL = "http://hinge-ue1-dev-cli-android-homework.s3-website-us-east-1.amazonaws.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val serviceApi: ProfileOrderServicesApi
        get() =  retrofit.create(ProfileOrderServicesApi::class.java)

}