package com.example.redteapotdating.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.network.NetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class UsersRepository {
    private val networkConn = NetworkConnection

    private val usersInfoLiveData : MutableLiveData<UsersInfo> = MutableLiveData<UsersInfo>()
    val listOfUsers : LiveData<UsersInfo> get() = usersInfoLiveData

    private val profileConfiguration : MutableLiveData<ProfileConfig> = MutableLiveData<ProfileConfig>()
    val config : LiveData<ProfileConfig> get() = profileConfiguration


    private fun getUserDataApi() {
        val call = networkConn.serviceApi.getAllUsers()
        val listOfUsers = mutableListOf<User>()


        call.enqueue(object : Callback<UsersInfo>{
            override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                println("Successful User Call")
               if (response.body() != null && response.isSuccessful){
                   val data = response.body()
                 for (c in data?.users!!){
                     val user = User(c.about,
                                c.gender,c.hobbies,
                                c.id,c.name,c.photo,c.school)
                     listOfUsers.add(user)
                 }
                   val dataObject = UsersInfo(listOfUsers)
                   usersInfoLiveData.value = dataObject
               }
                getProfileConfigApi()
            }
            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                 if (t is SocketTimeoutException) {
                     println("Timed Out User Call")
                } else {
                    println("Failed User Call")
                }
            }

        })
    }

   private fun getProfileConfigApi() {
        val call = networkConn.serviceApi.getConfig()

       call.enqueue(object : Callback<ProfileConfig>{
           override fun onResponse(call: Call<ProfileConfig>, response: Response<ProfileConfig>) {
               println("Successful Profile Config Call")
               if (response.body() != null && response.isSuccessful) {
                   val data = response.body()
                   val profileConfig = ProfileConfig(data?.profile!!)
                   profileConfiguration.value = profileConfig
               }
           }

           override fun onFailure(call: Call<ProfileConfig>, t: Throwable) {
               println("Failed Profile Config Call")
           }

       })
    }

    fun getUsersData() {
        CoroutineScope(Dispatchers.IO).launch {
             getUserDataApi()
        }
    }

    fun getUsersLiveData() : LiveData<UsersInfo> {
       return listOfUsers
    }
    fun getConfigLiveData() : LiveData<ProfileConfig> {
        return config
    }

}