package com.example.redteapotdating.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redteapotdating.R
import com.example.redteapotdating.databinding.ProfileItemLayoutBinding
import com.example.redteapotdating.model.User

class UserViewAdapter (currentUser : User): RecyclerView.Adapter<UserViewAdapter.ProfileDataViewHolder>() {
    private var currUser = currentUser
    private lateinit var binding : ProfileItemLayoutBinding

    fun updateData (currentUser : User?) {
        if (currentUser != null) {
            currUser = currentUser
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDataViewHolder {
       val context = parent.context
       val inflater =  LayoutInflater.from(context)
         binding = ProfileItemLayoutBinding.inflate(inflater,parent,false)
        return  ProfileDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileDataViewHolder, position: Int) {
        when (holder){
            is ProfileDataViewHolder -> {
                holder.bind(currUser)
                 holder.bindProfilePic(currUser.photo)
                if (!currUser.hobbies.isNullOrEmpty()) {
                    holder.bindHobbies(currUser.hobbies!!)
                    binding.hobbiesSection.visibility = View.VISIBLE
                }
                else binding.hobbiesSection.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
       return 1
    }

    class ProfileDataViewHolder(binding: ProfileItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private var nameTv = binding.tvName
        private var schoolSection = binding.schoolSection
        private var aboutSection = binding.aboutSection
        private var schoolTv = binding.tvSchoolInfo
        private var aboutTv = binding.tvAboutInfo
        private var genderTv = binding.tvGenderInfo
         var hobbiesTv = binding.tvHobbiesInfo
         var photoTv = binding.profilePic

        fun bind(currUser: User){
           if (currUser.about == null)  aboutSection.visibility=View.GONE
           else { aboutTv.text = currUser.about
               aboutSection.visibility=View.VISIBLE
           }
            if (currUser.school == null)  schoolSection.visibility=View.GONE
            else {
                schoolSection.visibility=View.VISIBLE
                schoolTv.text = currUser.school
            }
               nameTv.text = currUser.name
            genderTv.text = currUser.gender
            schoolTv.text = currUser.school
        }

        fun bindProfilePic(url : String?){
            Glide.with(photoTv.context)
                .load(url)
                .error(R.drawable.blank_user)
                .into(photoTv)
        }
        fun bindHobbies(hobbies : List<String>){
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
            hobbiesTv.text = hobbiesList.toString()
        }

    }
}