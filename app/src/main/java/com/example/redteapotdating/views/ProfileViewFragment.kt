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
    private var CURRENT_USER_INDEX = 0
    private val CURRENT_USER_INDEX_KEY = "CURRENT_USER_INDEX_KEY"
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
        getFragArguments()
        initViewModel()
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewmodel.listOfUsers.observe(viewLifecycleOwner){
            val users = it.users
            if (!users.isNullOrEmpty()) {
                val currUser = users[CURRENT_USER_INDEX]
                updateUI(currUser)
            }
            initUi()
        }
    }

    private fun updateUI(currUser: User) {
        binding.tvAboutInfo.text = currUser.about
        binding.tvName.text = currUser.name
        binding.tvGenderInfo.text = currUser.gender
        binding.tvSchoolInfo.text = currUser.school

        Glide.with(requireContext())
            .load(currUser.photo)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.profilePic)

        val hobbies = currUser.hobbies
        if (hobbies != null) {
            val hobbiesList = StringBuilder("")
            for (hobby in hobbies){
                hobbiesList.append("$hobby, ")
            }
            binding.tvHobbiesInfo.text = hobbiesList.toString()
        }
    }

    private fun getFragArguments() {
        val args = arguments
        CURRENT_USER_INDEX = args?.getInt(CURRENT_USER_INDEX_KEY) ?: 0
    }

    private fun initListeners() {

            binding.nextFAB.setOnClickListener{
                val nextUser = (CURRENT_USER_INDEX + 1)

                val bndl = Bundle()
                bndl.putInt("CURRENT_USER_INDEX_KEY", nextUser)

                val fragManager = requireActivity().supportFragmentManager
                val newFrag = ProfileViewFragment()
                newFrag.arguments = bndl
                fragManager.beginTransaction()
                    .replace(R.id.main_content,newFrag)
                    .disallowAddToBackStack()
                    .commit()
            }


            binding.prevFAB.setOnClickListener{
                val previousUser = (CURRENT_USER_INDEX - 1)

                val bndl = Bundle()
                bndl.putInt("CURRENT_USER_INDEX_KEY", previousUser)

                val fragManager = requireActivity().supportFragmentManager
                val newFrag = ProfileViewFragment()
                newFrag.arguments = bndl
                fragManager.beginTransaction()
                    .replace(R.id.main_content,newFrag)
                    .disallowAddToBackStack()
                    .commit()
            }

        binding.searchFAB.setOnClickListener{
            dataRetrieval()
        }

    }

    private fun initUi() {
        if (viewmodel.getCurrentUsers()?.users?.isNotEmpty() == true) {
            if((CURRENT_USER_INDEX+1 >= viewmodel.getCurrentUsers()?.users?.size!!) || viewmodel.getCurrentUsers()?.users?.size!! == 1) {
                binding.nextFAB.visibility = View.GONE
            } else {
                binding.nextFAB.visibility = View.VISIBLE
            }

            if(CURRENT_USER_INDEX <= 0) {
                binding.prevFAB.visibility = View.GONE
            } else {
                binding.prevFAB.visibility = View.VISIBLE
            }
        }  else {
            binding.nextFAB.visibility = View.GONE
            binding.prevFAB.visibility = View.GONE
        }
    }

    private fun dataRetrieval() {
        viewmodel.getUsers()
   //     viewmodel.getProfileConfig()
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this@ProfileViewFragment)[ProfileViewModel::class.java]
    }
}