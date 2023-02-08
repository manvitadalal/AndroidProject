package com.redteapotdating.profileorder.repository.model.user

import com.google.gson.annotations.SerializedName

data class ResponseListUsers(
    @SerializedName("users")
    var users: List<User>
)
