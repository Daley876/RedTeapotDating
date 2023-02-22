package com.example.redteapotdating.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User
import com.example.redteapotdating.model.UsersInfo
import com.example.redteapotdating.network.interfaces.ProfileConfigCallbackInterface
import com.example.redteapotdating.network.interfaces.UserInfoCallbackInterface
import com.example.redteapotdating.repository.UsersRepository

class ProfileViewModel : ViewModel() {
    private val repo = UsersRepository()

    private var nextBtn : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var currUserIndex : MutableLiveData<Int> = MutableLiveData(0)
    private var currentUser : MutableLiveData<User> = MutableLiveData<User>()
    private var listOfUserMutableLive : MutableLiveData<UsersInfo> = MutableLiveData<UsersInfo>()
    private var configMutableLiveData : MutableLiveData<ProfileConfig> = MutableLiveData<ProfileConfig>()

    //observable values in repo that are updated after each api call
    val listOfUsers : LiveData<UsersInfo> get() = listOfUserMutableLive
    val config : LiveData<ProfileConfig> get() = configMutableLiveData
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

     fun retrieveUsersApiCall(){
         repo.getUserDataApi(object : UserInfoCallbackInterface {
             override fun onSuccess(response: UsersInfo) {
                 listOfUserMutableLive.value = response
             }
             override fun onFailure(error: String) {
                 listOfUserMutableLive.value = UsersInfo(listOf())
                 Log.e("PROFILE CONFIG API","Could not retrieve UserInfo from endpoint /config. Error received was: $error")
             }
         })

         repo.getProfileConfigApi(object : ProfileConfigCallbackInterface {
             override fun onSuccess(response: ProfileConfig) {
                 configMutableLiveData.value = response
             }

             override fun onFailure(error: String) {
                 configMutableLiveData.value = ProfileConfig(listOf())
                 Log.e("PROFILE CONFIG API","Could not retrieve UserInfo from endpoint /config. Error received was: $error")
             }
         })
    }

}