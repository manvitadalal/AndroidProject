package com.example.redteapotdating.api

import com.example.redteapotdating.model.ResponseListUsers
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/users")
    suspend fun getAllUsers(): Response<ResponseListUsers>
}