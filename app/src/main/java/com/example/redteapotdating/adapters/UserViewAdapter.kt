package com.example.redteapotdating.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redteapotdating.R
import com.example.redteapotdating.databinding.ProfileItemLayoutBinding
import com.example.redteapotdating.model.User

class UserViewAdapter (context : Context, currentUser : User): RecyclerView.Adapter<UserViewAdapter.ProfileDataViewHolder>() {
    private var currUser = currentUser
    private lateinit var binding : ProfileItemLayoutBinding
    private val adapterContext = context

    fun updateData (currentUser : User?) {
        if (currentUser != null) {
            currUser = currentUser
            navAnimation()
            notifyDataSetChanged()
        }
    }

    private fun navAnimation() {
        val fadeOutAnimation = AnimationUtils.loadAnimation(adapterContext, R.anim.fade_out)
        fadeOutAnimation.duration = 1500
        val fadeInAnimation = AnimationUtils.loadAnimation(adapterContext, R.anim.fade_in)
        fadeInAnimation.duration = 1500

        binding.mainProfileLayout.startAnimation(fadeOutAnimation)
        binding.mainProfileLayout.startAnimation(fadeInAnimation)
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
        private var name_tv = binding.tvName
        private var schoolSection = binding.schoolSection
        private var aboutSection = binding.aboutSection
        private var school_tv = binding.tvSchoolInfo
        private var about_tv = binding.tvAboutInfo
        private var gender_tv = binding.tvGenderInfo
         var hobbies_tv = binding.tvHobbiesInfo
         var photo_tv = binding.profilePic

        fun bind(currUser: User){
           if (currUser.about == null)  aboutSection.visibility=View.GONE
           else { about_tv.text = currUser.about
               aboutSection.visibility=View.VISIBLE
           }
            if (currUser.school == null)  schoolSection.visibility=View.GONE
            else {schoolSection.visibility=View.VISIBLE
                school_tv.text = currUser.school}
               name_tv.text = currUser.name
            gender_tv.text = currUser.gender
            school_tv.text = currUser.school
        }

        fun bindProfilePic(url : String?){
            Glide.with(photo_tv.context)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(photo_tv)
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
            hobbies_tv.text = hobbiesList.toString()
        }

    }
}