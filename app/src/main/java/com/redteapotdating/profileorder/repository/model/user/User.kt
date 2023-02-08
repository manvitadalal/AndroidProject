package com.redteapotdating.profileorder.repository.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @SerializedName("id")
    @PrimaryKey
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("gender")
    var gender: String,

    @SerializedName("about")
    var about: String?,

    @SerializedName("photo")
    var photo: String?,

    @SerializedName("school")
    var school: String?,

    @SerializedName("hobbies")
    var hobbies: ArrayList<String>?
)