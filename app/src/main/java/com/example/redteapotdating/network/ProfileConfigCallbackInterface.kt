package com.example.redteapotdating.network

import com.example.redteapotdating.model.ProfileConfig

interface ProfileConfigCallbackInterface {

    fun onSuccess(response: ProfileConfig)

    fun onFailure(error : String)
}