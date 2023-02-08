package com.redteapotdating.profileorder.repository.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun listToJson(value: ArrayList<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): ArrayList<String>? = Gson().fromJson<ArrayList<String>?>(value, object :
        TypeToken<ArrayList<String>>() {}.type)
}