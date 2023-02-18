package com.example.redteapotdating.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.redteapotdating.R
import com.example.redteapotdating.databinding.ProfileItemLayoutBinding
import com.example.redteapotdating.model.User
import com.example.redteapotdating.viewmodel.ProfileViewModel

class ProfileViewFragment : Fragment() {
    private lateinit var viewmodel : ProfileViewModel
    private lateinit var binding : ProfileItemLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileItemLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewmodel.currentUser.observe(viewLifecycleOwner){
            val currUser = it
            updateUI(currUser)
            navButtonsVisibility()
        }
    }
    private fun updateUI(currUser: User) {
        binding.tvAboutInfo.text = currUser.about
        binding.tvName.text = currUser.name
        binding.tvGenderInfo.text = currUser.gender
        binding.tvSchoolInfo.text = currUser.school
        setUserProfilePic(currUser)
        setUserHobbies(currUser)
    }

    private fun setUserHobbies(currUser: User) {
        val hobbies = currUser.hobbies
        if (hobbies != null) {
            val hobbiesList = StringBuilder("")
            for (i in hobbies.indices){
                if (i == hobbies.size-1){
                    val hobby = hobbies[i]
                    hobbiesList.append(hobby)
                } else {
                    val hobby = hobbies[i]
                    hobbiesList.append("$hobby, ")
                }
            }
            binding.tvHobbiesInfo.text = hobbiesList.toString()
        }
    }

    private fun setUserProfilePic(currUser: User) {
        Glide.with(requireContext())
            .load(currUser.photo)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.profilePic)
    }


    private fun initListeners() {

            binding.nextFAB.setOnClickListener{
                viewmodel.updateIndexToNextUser()
            }

            binding.prevFAB.setOnClickListener{
                viewmodel.updateIndexToLastUser()
            }

        binding.searchFAB.setOnClickListener{
            dataRetrieval()
        }

    }

    private fun navButtonsVisibility() {
        if (viewmodel.getAllLatestUsers() == null || viewmodel.getAllLatestUsers()!!.users.isEmpty()) {
            binding.nextFAB.visibility = View.INVISIBLE
            binding.prevFAB.visibility = View.INVISIBLE
        } else {
            if (viewmodel.getCurrUserIndex() <= 0) {
                binding.prevFAB.visibility = View.INVISIBLE
            } else {
                binding.prevFAB.visibility = View.VISIBLE
            }
            if (viewmodel.getCurrUserIndex() + 1 >= viewmodel.getAllLatestUsers()!!.users.size) {
                binding.nextFAB.visibility = View.INVISIBLE
            } else {
                binding.nextFAB.visibility = View.VISIBLE
            }
        }
    }

    private fun dataRetrieval() {
        viewmodel.getUsersApiCall()
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this@ProfileViewFragment)[ProfileViewModel::class.java]
    }
}