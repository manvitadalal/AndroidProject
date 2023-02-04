package com.example.redteapotdating.model

import com.google.gson.annotations.SerializedName

data class ResponseListUsers(
    @SerializedName("users")
    var users: List<User>
)
