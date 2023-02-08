package com.redteapotdating.profileorder.repository.model.user

import com.google.gson.annotations.SerializedName

// This represents the response object for the 'user' endpoint
data class ResponseListUsers(
    @SerializedName("users")
    var users: List<User>
)
