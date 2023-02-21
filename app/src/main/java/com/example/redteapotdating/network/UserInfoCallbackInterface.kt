package com.example.redteapotdating.network

import com.example.redteapotdating.model.UsersInfo

interface UserInfoCallbackInterface {

    fun onSuccess(response: UsersInfo)

    fun onFailure(error : String)
}