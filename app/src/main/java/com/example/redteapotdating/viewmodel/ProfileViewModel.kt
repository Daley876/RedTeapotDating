package com.example.redteapotdating.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.repository.UsersRepository

class ProfileViewModel : ViewModel() {
    private val repo = UsersRepository()


   /* private var userName : MutableLiveData<String> = MutableLiveData<String>()
    private var userHobbies : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    private var userAbout : MutableLiveData<String> = MutableLiveData<String>()
    private var userGender : MutableLiveData<String> = MutableLiveData<String>()
    private var userSchool : MutableLiveData<String> = MutableLiveData<String>()
*/

    val listOfUsers : LiveData<UsersInfo> get() = repo.getUsersLiveData()
    val config : LiveData<ProfileConfig> get() = repo.getConfigLiveData()


    fun getCurrentUsers() : UsersInfo? {
        return listOfUsers.value
    }

    fun getProfileConfiguration() : ProfileConfig? {
        return config.value
    }

     fun getUsers(){
         repo.getUsers()
    }

     fun getProfileConfig(){
          repo.getProfileConfig()

    }

}