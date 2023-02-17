package com.example.redteapotdating.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redteapotdating.repository.UsersRepository

class ProfileViewModel : ViewModel() {
    private val repo = UsersRepository()


    private var userName : MutableLiveData<String> = MutableLiveData<String>()
    private var userHobbies : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    private var userAbout : MutableLiveData<String> = MutableLiveData<String>()
    private var userGender : MutableLiveData<String> = MutableLiveData<String>()
    private var userSchool : MutableLiveData<String> = MutableLiveData<String>()

    fun getUsers(){
        repo.getUsers()
    }
    fun getProfileConfig(){
        repo.getProfileConfig()
    }
}