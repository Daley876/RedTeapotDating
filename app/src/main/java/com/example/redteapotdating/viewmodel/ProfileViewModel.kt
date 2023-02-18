package com.example.redteapotdating.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.repository.UsersRepository

class ProfileViewModel : ViewModel() {
    private val repo = UsersRepository()

    //observable values in repo that are updated after each api call
    private val listOfUsers : LiveData<UsersInfo> get() = repo.getUsersLiveData()
    val config : LiveData<ProfileConfig> get() = repo.getConfigLiveData()

    private var currUserIndex : MutableLiveData<Int> = MutableLiveData(0)
     var currentUser : MutableLiveData<User> = MutableLiveData<User>()


    fun getAllLatestUsers() : UsersInfo? {
        return listOfUsers.value
    }

    fun getCurrUserIndex () : Int {
       return currUserIndex.value!!
    }

    private fun updateCurrentUser() {
        val index = currUserIndex.value
        val users = listOfUsers.value?.users
        if (!users.isNullOrEmpty()) {
            //currentUser.postValue(users[index!!])
            currentUser.value = users[index!!]
        }
    }

    fun updateIndexToNextUser(){
        val currIndex = currUserIndex.value
        val newIndex = currIndex?.plus(1)
        currUserIndex.value = newIndex!!
        updateCurrentUser()
    }

    fun updateIndexToLastUser(){
        val currIndex = currUserIndex.value
        val newIndex = currIndex?.minus(1)
        currUserIndex.value = newIndex!!
        updateCurrentUser()
    }

    fun getProfileConfigurationValue() : ProfileConfig? {
        return config.value
    }

     fun getUsersApiCall(){
         repo.getUsersData()
    }

}