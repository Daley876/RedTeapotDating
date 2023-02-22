package com.example.redteapotdating.network.interfaces

import com.example.redteapotdating.model.UsersInfo

interface UserInfoCallbackInterface {

    fun onSuccess(response: UsersInfo)

    fun onFailure(error : String)
}