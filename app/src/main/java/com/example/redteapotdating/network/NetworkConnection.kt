package com.example.redteapotdating.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConnection {
    private const val BASE_URL = "http://hinge-ue1-dev-cli-android-homework.s3-website-us-east-1.amazonaws.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

     val serviceApi: MyApiServices = retrofit.create(MyApiServices::class.java)

   // val connect = ServiceConnection(serviceApi)

}