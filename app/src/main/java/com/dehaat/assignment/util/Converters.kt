package com.dehaat.assignment.util

import androidx.room.TypeConverter
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: List<BooksSearchResponse>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<BooksSearchResponse>::class.java).toList()
}