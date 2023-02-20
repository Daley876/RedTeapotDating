package com.example.redteapotdating.repository

import android.util.Log
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

class UsersRepository {
    private val networkConn = NetworkConnection

    private val usersInfoLiveData : MutableLiveData<UsersInfo> = MutableLiveData<UsersInfo>()
    val listOfUsers : LiveData<UsersInfo> get() = usersInfoLiveData

    private val profileConfiguration : MutableLiveData<ProfileConfig> = MutableLiveData<ProfileConfig>()
    val config : LiveData<ProfileConfig> get() = profileConfiguration


    private fun getUserDataApi() {
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
                   usersInfoLiveData.postValue(dataObject)
               }
            }
            override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                Log.e("USER INFO API","Could not retrieve UserInfo from endpoint /users. Error received was: $t")
            }

        })
    }

   private fun getProfileConfigApi() {
        val call = networkConn.serviceApi.getConfig()

       call.enqueue(object : Callback<ProfileConfig>{
           override fun onResponse(call: Call<ProfileConfig>, response: Response<ProfileConfig>) {
               if (response.body() != null && response.isSuccessful) {
                   val data = response.body()
                   val profileConfig = ProfileConfig(data?.profile!!)
                   profileConfiguration.postValue(profileConfig)
               }
           }

           override fun onFailure(call: Call<ProfileConfig>, t: Throwable) {
               Log.e("PROFILE CONFIG API","Could not retrieve UserInfo from endpoint /config. Error received was: $t")
           }

       })
    }

    fun retrieveUsersData() {
        CoroutineScope(Dispatchers.IO).launch {
             getUserDataApi()
            getProfileConfigApi()
        }
    }

    fun getUsersLiveData() : LiveData<UsersInfo> {
       return listOfUsers
    }
    fun getConfigLiveData() : LiveData<ProfileConfig> {
        return config
    }

}