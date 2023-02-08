package com.redteapotdating.profileorder.repository.repo

import android.util.Log
import com.redteapotdating.profileorder.repository.api.ApiInterface
import com.redteapotdating.profileorder.repository.model.config.Config
import com.redteapotdating.profileorder.repository.model.user.ResponseListUsers
import retrofit2.Response

class UserRepository(private val apiInterface: ApiInterface) {

    //attempts to call users endpoint
    suspend fun getUsers(): Response<ResponseListUsers>? {
        return kotlin.runCatching {
            apiInterface.getAllUsers()
        }.getOrElse { r ->
            r.localizedMessage?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }

    //attempts to call config endpoint
    suspend fun getConfig(): Response<Config>? {
        return kotlin.runCatching {
            apiInterface.getConfig()
        }.getOrElse { r ->
            r.message?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }
}