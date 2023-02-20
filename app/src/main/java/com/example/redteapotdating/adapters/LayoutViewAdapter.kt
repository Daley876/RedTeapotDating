package com.example.redteapotdating.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redteapotdating.R
import com.example.redteapotdating.databinding.*
import com.example.redteapotdating.model.ProfileConfig
import com.example.redteapotdating.model.User

class LayoutViewAdapter(currUser: User, config: ProfileConfig) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var profileConfig = config
    private var currentUser = currUser

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
                nameHolder.bind(currentUser)
            }
            ITEM_ABOUT -> {
                val aboutHolder = holder as AboutViewHolder
                aboutHolder.bind(currentUser)
            }
            ITEM_SCHOOL -> {
                val schoolHolder = holder as SchoolViewHolder
                schoolHolder.bind(currentUser)
            }
            ITEM_GENDER -> {
                val genderHolder = holder as GenderViewHolder
                genderHolder.bind(currentUser)
            }
            ITEM_HOBBIES -> {
                val hobbiesHolder = holder as HobbiesViewHolder
                hobbiesHolder.bindHobbies(currentUser.hobbies!!)
            }
            ITEM_PHOTO -> {
                val photoHolder = holder as PhotoViewHolder
                photoHolder.bindProfilePic(currentUser.photo, currentUser.gender)
            }
        }
    }

    override fun getItemCount(): Int {
        return profileConfig.profile.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (profileConfig.profile[position].equals("name", ignoreCase = true)) {
            ITEM_NAME
        } else if (profileConfig.profile[position].equals("school", ignoreCase = true)) {
            if (currentUser.school.isNullOrEmpty()) ITEM_EMPTY else ITEM_SCHOOL
        } else if (profileConfig.profile[position].equals("hobbies", ignoreCase = true)) {
            if (currentUser.hobbies.isNullOrEmpty()) ITEM_EMPTY else ITEM_HOBBIES
        } else if (profileConfig.profile[position].equals("about", ignoreCase = true)) {
            if (currentUser.about.isNullOrEmpty()) ITEM_EMPTY else ITEM_ABOUT
        } else if (profileConfig.profile[position].equals("photo", ignoreCase = true)) {
            ITEM_PHOTO
        } else if (profileConfig.profile[position].equals("gender", ignoreCase = true)) {
            ITEM_GENDER
        } else ITEM_EMPTY
    }

    fun updateUserData(currUser: User?) {
        if (currUser != null) {
            currentUser = currUser
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

            val genderToSet = if (currUser.gender.equals("m", ignoreCase = true)
                || currUser.gender.equals("male", ignoreCase = true)) "Male"
            else "Female"

            genderTv.text = genderToSet
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

        fun bindProfilePic(url: String?, gender: String) {
            val errorImg = if (gender.equals("f", ignoreCase = true)
                || gender.equals("female", ignoreCase = true)) R.drawable.ic_baseline_person_f_24
            else R.drawable.blank_user

            Glide.with(photoTv.context)
                .load(url)
                .error(errorImg)
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