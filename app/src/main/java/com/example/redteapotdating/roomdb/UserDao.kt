package com.example.redteapotdating.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.redteapotdating.model.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(user: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("Select * From users")
    fun getAllUsers(): List<User>

    @Delete
    suspend fun deleteUser(user: User)
}