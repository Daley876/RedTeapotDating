package com.example.redteapotdating.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redteapotdating.R
import com.example.redteapotdating.databinding.*
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User

class LayoutViewAdapter(currentUser: User, config: ProfileConfig) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var profileConfig = config
    private var currUser = currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            ITEM_NAME -> {
                val binding = ProfileItemNameLayoutBinding.inflate(layoutInflater, parent, false)
                NameViewHolder(binding)
            }
            ITEM_SCHOOL -> {
                val binding = ProfileItemSchoolLayoutBinding.inflate(layoutInflater, parent, false)
                SchoolViewHolder(binding)
            }
            ITEM_ABOUT -> {
                val binding = ProfileItemAboutLayoutBinding.inflate(layoutInflater, parent, false)
                AboutViewHolder(binding)
            }
            ITEM_GENDER -> {
                val binding = ProfileItemGenderLayoutBinding.inflate(layoutInflater, parent, false)
                GenderViewHolder(binding)
            }
            ITEM_PHOTO -> {
                val binding = ProfileItemPhotoLayoutBinding.inflate(layoutInflater, parent, false)
                PhotoViewHolder(binding)
            }
            ITEM_HOBBIES -> {
                val binding = ProfileItemHobbiesLayoutBinding.inflate(layoutInflater, parent, false)
                HobbiesViewHolder(binding)
            }
            else -> {
                val binding = EmptyProfileItemLayoutBinding.inflate(layoutInflater, parent, false)
                EmptyDataViewHolder(binding)
            }
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
                hobbiesHolder.bindHobbies(currUser.hobbies!!)
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
        return if (profileConfig.profile[position].equals("name", ignoreCase = true)) {
            ITEM_NAME
        }
        else if (profileConfig.profile[position].equals("school", ignoreCase = true)) {
            if (currUser.school.isNullOrEmpty()) ITEM_EMPTY else ITEM_SCHOOL
        }
        else if (profileConfig.profile[position].equals("hobbies", ignoreCase = true)) {
            if (currUser.hobbies.isNullOrEmpty()) ITEM_EMPTY else ITEM_HOBBIES
        }
        else if (profileConfig.profile[position].equals("about", ignoreCase = true)) {
            if (currUser.about.isNullOrEmpty()) ITEM_EMPTY else ITEM_ABOUT
        }
        else if (profileConfig.profile[position].equals("photo", ignoreCase = true)) {
            ITEM_PHOTO
        }
        else if (profileConfig.profile[position].equals("gender", ignoreCase = true)) {
            if (currUser.photo.isNullOrEmpty()) ITEM_EMPTY else ITEM_GENDER
        }
        else ITEM_EMPTY
    }

    fun updateUserData(currentUser: User?) {
        if (currentUser != null) {
            currUser = currentUser
            notifyDataSetChanged()
        }
    }

    fun updateConfigData(configData: ProfileConfig?) {
        if (configData != null) {
            profileConfig = configData
            notifyDataSetChanged()
        }
    }

    class NameViewHolder(binding: ProfileItemNameLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val nameTv = binding.tvName

        fun bind(currUser: User) {
            nameTv.text = currUser.name
        }

    }

    class AboutViewHolder(binding: ProfileItemAboutLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val aboutTv = binding.tvAboutInfo

        fun bind(currUser: User) {
            aboutTv.text = currUser.about
        }
    }

    class SchoolViewHolder(binding: ProfileItemSchoolLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val schoolTv = binding.tvSchoolInfo

        fun bind(currUser: User) {
            schoolTv.text = currUser.school
        }
    }

    class GenderViewHolder(binding: ProfileItemGenderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val genderTv = binding.tvGenderInfo

        fun bind(currUser: User) {
            genderTv.text = currUser.gender
        }
    }

    class HobbiesViewHolder(binding: ProfileItemHobbiesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val hobbiesTv = binding.tvHobbiesInfo

        fun bindHobbies(hobbies: List<String>) {
            val hobbiesList = StringBuilder("")
            for (i in hobbies.indices) {
                if (i == hobbies.size - 1) {
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

    class PhotoViewHolder(binding: ProfileItemPhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val photoTv = binding.profilePic

        fun bindProfilePic(url: String?) {
                Glide.with(photoTv.context)
                    .load(url)
                    .error(R.drawable.blank_user)
                    .into(photoTv)
        }
    }

    class EmptyDataViewHolder(binding: EmptyProfileItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val ITEM_NAME = 1001
        const val ITEM_PHOTO = 2001
        const val ITEM_GENDER = 3001
        const val ITEM_ABOUT = 4001
        const val ITEM_SCHOOL = 5001
        const val ITEM_HOBBIES = 6001
        const val ITEM_EMPTY = 9999
    }
}