package com.example.redteapotdating.repository

import android.util.Log
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.network.NetworkConnection
import com.example.redteapotdating.network.ProfileConfigCallbackInterface
import com.example.redteapotdating.network.UserInfoCallbackInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository {
    private val networkConn = NetworkConnection

    fun getUserDataApi(callback : UserInfoCallbackInterface) {
        val call = networkConn.serviceApi.getAllUsers()
        val listOfCurrUsers = mutableListOf<User>()

        call.enqueue(object : Callback<UsersInfo>{
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
               if (response.body() != null && response.isSuccessful){
                   val data = response.body()
                 for (c in data?.users!!){
                     val user = User(c.about,
                                c.gender,c.hobbies,
                                c.id,c.name,c.photo,c.school)
                     listOfCurrUsers.add(user)
                 }
                   val dataObject = UsersInfo(listOfCurrUsers)
                   callback.onSuccess(dataObject)
               }
            }
            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                callback.onFailure(t.toString())
                Log.e("USER INFO API","Could not retrieve UserInfo from endpoint /users. Error received was: $t")
            }

        })
    }

    fun getProfileConfigApi(callback : ProfileConfigCallbackInterface) {
        val call = networkConn.serviceApi.getConfig()

       call.enqueue(object : Callback<ProfileConfig>{
           override fun onResponse(call: Call<ProfileConfig>, response: Response<ProfileConfig>) {
               if (response.body() != null && response.isSuccessful) {
                   val data = response.body()
                   val profileConfig = ProfileConfig(data?.profile!!)
                   callback.onSuccess(profileConfig)
               }
           }

           override fun onFailure(call: Call<ProfileConfig>, t: Throwable) {
               callback.onFailure(t.toString())
               Log.e("PROFILE CONFIG API","Could not retrieve UserInfo from endpoint /config. Error received was: $t")
           }

       })
    }
}