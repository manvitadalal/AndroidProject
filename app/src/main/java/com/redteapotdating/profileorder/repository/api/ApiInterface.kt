package com.redteapotdating.profileorder.repository.api

import com.redteapotdating.profileorder.repository.model.config.Config
import com.redteapotdating.profileorder.repository.model.user.ResponseListUsers
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/users")
    suspend fun getAllUsers(): Response<ResponseListUsers>

    @GET("/config")
    suspend fun getConfig(): Response<Config>
}