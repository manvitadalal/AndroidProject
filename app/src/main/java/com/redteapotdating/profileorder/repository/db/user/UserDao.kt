package com.redteapotdating.profileorder.repository.db.user

import androidx.room.*
import com.redteapotdating.profileorder.repository.model.user.User


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