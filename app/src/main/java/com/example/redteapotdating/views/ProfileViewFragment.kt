package com.example.redteapotdating.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redteapotdating.adapters.UserViewAdapter
import com.example.redteapotdating.databinding.MainViewLayoutBinding
import com.example.redteapotdating.model.User
import com.example.redteapotdating.viewmodel.ProfileViewModel

class ProfileViewFragment : Fragment() {
    private lateinit var viewmodel : ProfileViewModel
    private lateinit var binding : MainViewLayoutBinding
    private lateinit var userViewAdapter: UserViewAdapter
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
        binding.recyclerViewIncluded.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userViewAdapter
        }
        initListeners()
    }

    private fun initAdapters() {
        userViewAdapter = UserViewAdapter(requireContext(),User(null,"", listOf(),0,"",null,""))
    }

    private fun initObservers() {
        viewmodel.currentUser.observe(viewLifecycleOwner){
            val currUser = it
            userViewAdapter.updateData(currUser)
        }

        viewmodel.listOfUsers.observe(viewLifecycleOwner){
            viewmodel.updateCurrentUser()
        }

        viewmodel.nextBtnLiveData.observe(viewLifecycleOwner){
            val bool = it
            if (bool) binding.nextFAB.visibility = View.VISIBLE
            else binding.nextFAB.visibility = View.INVISIBLE
        }
    }



    private fun initListeners() {

            binding.nextFAB.setOnClickListener{
                viewmodel.updateIndexToNextUser()
            }

        dataRetrieval()
    }


    private fun dataRetrieval() {
        viewmodel.getUsersApiCall()
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this@ProfileViewFragment)[ProfileViewModel::class.java]
    }
}