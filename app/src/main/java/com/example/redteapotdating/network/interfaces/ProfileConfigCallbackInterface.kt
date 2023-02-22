package com.example.redteapotdating.network.interfaces

import com.example.redteapotdating.model.ProfileConfig

interface ProfileConfigCallbackInterface {

    fun onSuccess(response: ProfileConfig)

    fun onFailure(error : String)
}