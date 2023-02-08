package com.redteapotdating.profileorder.repository.repo

import android.util.Log
import com.redteapotdating.profileorder.repository.api.ApiInterface
import com.redteapotdating.profileorder.repository.db.user.UserDatabase
import com.redteapotdating.profileorder.repository.model.config.Config
import com.redteapotdating.profileorder.repository.model.user.ResponseListUsers
import com.redteapotdating.profileorder.repository.model.user.User
import retrofit2.Response

class UserRepository(
    private val apiInterface: ApiInterface,
    private val userDatabase: UserDatabase
) {

    //this method calls /user endpoint to fetch userList
    suspend fun getUsers(): Response<ResponseListUsers>? {
        return kotlin.runCatching {
            apiInterface.getAllUsers()
        }.getOrElse { r ->
            r.message?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }

    //this method calls /config endpoint to fetch profile
    suspend fun getConfig(): Response<Config>? {
        return kotlin.runCatching {
            apiInterface.getConfig()
        }.getOrElse { r ->
            r.message?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }

    //this method performs insertion operation in the user database
    suspend fun insertUsersToDb(users: List<User>) {
        return kotlin.runCatching {
            userDatabase.userDao().insertUserList(users)
        }.getOrElse { r ->
            r.message?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }

    //this method gets the list of all users from the database
    fun getUsersFromDb(): List<User>? {
        return kotlin.runCatching {
            return userDatabase.userDao().getAllUsers()
        }.getOrElse { r ->
            r.message?.let { r.localizedMessage?.let { it1 -> Log.e("Error", it1) } }
            null
        }
    }
}