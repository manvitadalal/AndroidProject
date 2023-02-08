package com.redteapotdating.profileorder.repository.model.config

import com.google.gson.annotations.SerializedName

// This represents the response object for the 'config' endpoint
data class Config(
    @SerializedName("profile")
    var profile: ArrayList<String> = arrayListOf(
        "name",
        "photo",
        "gender",
        "about",
        "school",
        "hobbies"
    )
)