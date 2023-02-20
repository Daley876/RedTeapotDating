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

    private var nextBtn : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var currUserIndex : MutableLiveData<Int> = MutableLiveData(0)
    private var currentUser : MutableLiveData<User> = MutableLiveData<User>()

    //observable values in repo that are updated after each api call
    val listOfUsers : LiveData<UsersInfo> get() = repo.getUsersLiveData()
    val config : LiveData<ProfileConfig> get() = repo.getConfigLiveData()

    val nextBtnLiveData : LiveData<Boolean> get() = nextBtn
    val currentUserLiveData : LiveData<User> get() = currentUser


    private fun getAllLatestUsers() : UsersInfo? {
        return listOfUsers.value
    }

    private fun getCurrUserIndex () : Int {
       return currUserIndex.value!!
    }

    private fun navButtonsVisibility() {
        if (listOfUsers.value == null || listOfUsers.value!!.users.isEmpty()) {
            nextBtn.value = false
        } else {
            nextBtn.value = getCurrUserIndex() + 1 < getAllLatestUsers()!!.users.size
        }
    }

    fun updateCurrentUserOnScreen() {
        val index = currUserIndex.value
        val users = listOfUsers.value?.users
        if (!users.isNullOrEmpty()) {
            currentUser.postValue(users[index!!])
        }
        navButtonsVisibility()
    }

    fun updateIndexToNextUser(){
        val currIndex = currUserIndex.value
        val newIndex = currIndex?.plus(1)
        currUserIndex.value = newIndex!!
        updateCurrentUserOnScreen()
    }

     fun getUsersApiCall(){
         repo.getUsersData()
    }

}