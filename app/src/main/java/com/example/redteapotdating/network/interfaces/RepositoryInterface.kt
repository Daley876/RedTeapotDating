package com.example.redteapotdating.network.interfaces

interface RepositoryInterface {
    fun getUserDataApi(callback : UserInfoCallbackInterface)

    fun getProfileConfigApi(callback : ProfileConfigCallbackInterface)
}