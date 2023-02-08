package com.redteapotdating.profileorder.repository.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Converter class for converting ArrayList<String> objects to json format so it could be saved in RoomDb
class StringListConverter {
    @TypeConverter
    fun listToJson(value: ArrayList<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): ArrayList<String>? =
        Gson().fromJson<ArrayList<String>?>(value, object :
            TypeToken<ArrayList<String>>() {}.type)
}