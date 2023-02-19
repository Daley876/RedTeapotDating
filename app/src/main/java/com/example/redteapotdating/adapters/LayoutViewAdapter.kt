package com.example.redteapotdating.adapters

import android.view.LayoutInflater
import android.view.View
import com.example.redteapotdating.R
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redteapotdating.databinding.*
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User

class LayoutViewAdapter (currentUser : User, config : ProfileConfig): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var profileConfig = config
    private var currUser = currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            ITEM_NAME -> {
                val binding = ProfileItemNameLayoutBinding.inflate(layoutInflater,parent,false)
                NameViewHolder(binding)
            }
            ITEM_SCHOOL -> {
                val binding = ProfileItemSchoolLayoutBinding.inflate(layoutInflater,parent,false)
                SchoolViewHolder(binding)
            }
            ITEM_ABOUT -> {
                val binding = ProfileItemAboutLayoutBinding.inflate(layoutInflater,parent,false)
                AboutViewHolder(binding)
            }
            ITEM_GENDER -> {
                val binding = ProfileItemGenderLayoutBinding.inflate(layoutInflater,parent,false)
                GenderViewHolder(binding)
            }
            ITEM_PHOTO -> {
                val binding = ProfileItemPhotoLayoutBinding.inflate(layoutInflater,parent,false)
                PhotoViewHolder(binding)
            }
            ITEM_HOBBIES -> {
                val binding = ProfileItemHobbiesLayoutBinding.inflate(layoutInflater,parent,false)
                HobbiesViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_NAME -> {
                val nameHolder = holder as NameViewHolder
                nameHolder.bind(currUser)
            }
            ITEM_ABOUT -> {
                val aboutHolder = holder as AboutViewHolder
                aboutHolder.bind(currUser)
            }
            ITEM_SCHOOL -> {
                val schoolHolder = holder as SchoolViewHolder
                schoolHolder.bind(currUser)
            }
            ITEM_GENDER -> {
                val genderHolder = holder as GenderViewHolder
                genderHolder.bind(currUser)
            }
            ITEM_HOBBIES -> {
                val hobbiesHolder = holder as HobbiesViewHolder
                hobbiesHolder.bindHobbies(currUser.hobbies)
            }
            ITEM_PHOTO -> {
                val photoHolder = holder as PhotoViewHolder
                photoHolder.bindProfilePic(currUser.photo)
            }
        }
    }

    override fun getItemCount(): Int {
       return profileConfig.profile.size
    }

    override fun getItemViewType(position: Int): Int {
       if (profileConfig.profile[position].equals("name",ignoreCase = true)) {
           return ITEM_NAME
       }
        if (profileConfig.profile[position].equals("school",ignoreCase = true)) {
            return ITEM_SCHOOL
        }
        if (profileConfig.profile[position].equals("hobbies",ignoreCase = true)) {
            return ITEM_HOBBIES
        }
        if (profileConfig.profile[position].equals("about",ignoreCase = true)) {
            return ITEM_ABOUT
        }
        if (profileConfig.profile[position].equals("photo",ignoreCase = true)) {
            return ITEM_PHOTO
        }
        if (profileConfig.profile[position].equals("gender",ignoreCase = true)) {
            return ITEM_GENDER
        }
        return 0
    }

    fun updateUserData (currentUser : User?) {
        if (currentUser != null) {
            currUser = currentUser
            notifyDataSetChanged()
        }
    }
    fun updateConfigData (configData : ProfileConfig?) {
        if (configData != null) {
            profileConfig = configData
            notifyDataSetChanged()
        }
    }
    class NameViewHolder(binding: ProfileItemNameLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameTv = binding.tvName

        fun bind(currUser: User){
            nameTv.text = currUser.name
        }

    }
    class AboutViewHolder(binding: ProfileItemAboutLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val aboutSection = binding.aboutSection
        private val aboutTv = binding.tvAboutInfo

        fun bind(currUser: User) {
            if (currUser.about == null || currUser.about.isEmpty()) aboutSection.visibility = View.GONE
            else {
                aboutTv.text = currUser.about
                aboutSection.visibility = View.VISIBLE
            }
        }
    }
    class SchoolViewHolder(binding: ProfileItemSchoolLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val schoolSection = binding.schoolSection
        private val schoolTv = binding.tvSchoolInfo

        fun bind(currUser: User){
            if (currUser.school == null || currUser.school.isEmpty())  schoolSection.visibility=View.GONE
            else {
                schoolSection.visibility=View.VISIBLE
                schoolTv.text = currUser.school
            }
        }
    }
    class GenderViewHolder(binding: ProfileItemGenderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val genderTv = binding.tvGenderInfo

        fun bind(currUser: User){
            genderTv.text = currUser.gender
        }
    }
    class HobbiesViewHolder(binding: ProfileItemHobbiesLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val hobbiesTv = binding.tvHobbiesInfo
        private val hobbiesSection = binding.hobbiesSection

        fun bindHobbies(hobbies : List<String>?){
            if (hobbies.isNullOrEmpty()){
                hobbiesSection.visibility = View.GONE
            } else {
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
                hobbiesSection.visibility = View.VISIBLE
            }
        }
    }
    class PhotoViewHolder(binding: ProfileItemPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val photoTv = binding.profilePic

        fun bindProfilePic(url : String?){
            Glide.with(photoTv.context)
                .load(url)
                .error(R.drawable.blank_user)
                .into(photoTv)
        }
    }


    companion object {
        const val ITEM_NAME = 1
        const val ITEM_PHOTO = 2
        const val ITEM_GENDER = 3
        const val ITEM_ABOUT = 4
        const val ITEM_SCHOOL = 5
        const val ITEM_HOBBIES = 6

    }
}