package com.redteapotdating.profileorder.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redteapotdating.profileorder.repository.db.user.UserDatabase
import com.redteapotdating.profileorder.repository.model.user.User
import com.redteapotdating.profileorder.repository.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(
    private val userRepository: UserRepository,
    private val userDatabase: UserDatabase
) : ViewModel() {

    val userList = MutableLiveData<List<User>>()
    val config = MutableLiveData<ArrayList<String>>()

    fun setup() {
        CoroutineScope(Dispatchers.IO).launch {
            val configResponse = userRepository.getConfig()
            val userListResponse = userRepository.getUsers()
            withContext(Dispatchers.Main) {
                // Not storing configuration in database or shared preference as in such a scenario default config will still apply.
                if (configResponse != null && configResponse.isSuccessful) {
                    config.postValue(configResponse.body()!!.profile)
                }
            }
            if (userListResponse != null && userListResponse.isSuccessful) {
                if (userListResponse.body()?.users?.size!! > 0) {
                    userDatabase.userDao().insertUserList(userListResponse.body()!!.users)
                }
                userList.postValue(userListResponse.body()!!.users)
            } else {
                userList.postValue(userDatabase.userDao().getAllUsers())
            }
        }
    }
}

