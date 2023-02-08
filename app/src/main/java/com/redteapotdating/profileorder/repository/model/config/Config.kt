package com.redteapotdating.profileorder.repository.model.config

import com.google.gson.annotations.SerializedName

// This will be saved in a shared preference
data class Config(
    @SerializedName("profile")
    var profile: ArrayList<String> = arrayListOf("name", "photo", "gender", "about", "school", "hobbies")
)