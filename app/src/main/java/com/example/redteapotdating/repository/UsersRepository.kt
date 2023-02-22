package com.example.redteapotdating.repository

import android.util.Log
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.network.NetworkConnection
import com.example.redteapotdating.network.interfaces.ProfileConfigCallbackInterface
import com.example.redteapotdating.network.interfaces.RepositoryInterface
import com.example.redteapotdating.network.interfaces.UserInfoCallbackInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository : RepositoryInterface{
    private val networkConn = NetworkConnection

    override fun getUserDataApi(callback: UserInfoCallbackInterface) {
        val call = networkConn.serviceApi.getAllUsers()

        call.enqueue(object : Callback<UsersInfo> {
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                if (response.body() != null && response.isSuccessful) {
                    val data = response.body()
                    callback.onSuccess(data!!)
                }
            }

            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                callback.onFailure(t.toString())
                Log.e(
                    "USER INFO API",
                    "Could not retrieve UserInfo from endpoint /users. Error received was: $t"
                )
            }

        })
    }

    override fun getProfileConfigApi(callback: ProfileConfigCallbackInterface) {
        val call = networkConn.serviceApi.getConfig()

        call.enqueue(object : Callback<ProfileConfig> {
            override fun onResponse(call: Call<ProfileConfig>, response: Response<ProfileConfig>) {
                if (response.body() != null && response.isSuccessful) {
                    val data = response.body()
                    callback.onSuccess(data!!)
                }
            }

            override fun onFailure(call: Call<ProfileConfig>, t: Throwable) {
                callback.onFailure(t.toString())
                Log.e(
                    "PROFILE CONFIG API",
                    "Could not retrieve UserInfo from endpoint /config. Error received was: $t"
                )
            }

        })
    }
}