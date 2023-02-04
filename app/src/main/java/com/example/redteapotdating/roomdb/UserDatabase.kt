package com.example.redteapotdating.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.redteapotdating.model.User
import com.example.redteapotdating.roomdb.converters.StringListConverter

@Database(entities = [User::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun dao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        UserDatabase::class.java,
                        "userDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}