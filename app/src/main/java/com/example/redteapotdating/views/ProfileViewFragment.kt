package com.example.redteapotdating.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redteapotdating.R
import com.example.redteapotdating.adapters.LayoutViewAdapter
import com.example.redteapotdating.databinding.MainViewLayoutBinding
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User
import com.example.redteapotdating.viewmodel.ProfileViewModel

class ProfileViewFragment : Fragment() {
    private lateinit var viewmodel : ProfileViewModel
    private lateinit var binding : MainViewLayoutBinding
    private lateinit var layoutViewAdapter: LayoutViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainViewLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initViewModel()
        initObservers()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = layoutViewAdapter
        }
        initListeners()
        dataRetrieval()
    }

    private fun initAdapters() {
        val initialUser = User(null,"", listOf(),0,"",null,"")
        val initialConfig = ProfileConfig(listOf("name", "photo", "gender", "about", "school", "hobbies"))
        layoutViewAdapter = LayoutViewAdapter(initialUser, initialConfig)
    }

    private fun navProfileTransitions(){
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        fadeOutAnimation.duration = 400
        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeInAnimation.duration = 400

        binding.recyclerView.startAnimation(fadeOutAnimation)
        viewmodel.updateIndexToNextUser()
        binding.recyclerView.startAnimation(fadeInAnimation)
    }

    private fun initObservers() {
        viewmodel.currentUserLiveData.observe(viewLifecycleOwner){
            val currUser = it
            layoutViewAdapter.updateUserData(currUser)
        }

        viewmodel.config.observe(viewLifecycleOwner){
            val configChanges = it
            layoutViewAdapter.updateConfigData(configChanges)
        }
        viewmodel.listOfUsers.observe(viewLifecycleOwner){
            viewmodel.updateCurrentUserOnScreen()
        }

        viewmodel.nextBtnLiveData.observe(viewLifecycleOwner){
            val bool = it
            if (bool) binding.nextFAB.visibility = View.VISIBLE
            else binding.nextFAB.visibility = View.INVISIBLE
        }
    }

    private fun initListeners() {
            binding.nextFAB.setOnClickListener{
                navProfileTransitions()
            }
    }


    private fun dataRetrieval() {
        viewmodel.getUsersApiCall()
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this@ProfileViewFragment)[ProfileViewModel::class.java]
    }
}