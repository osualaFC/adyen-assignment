package com.adyen.android.assignment.data.cache.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class Converters {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromList(address: List<String>): String {
        return moshi.adapter<List<String>>(List::class.java).toJson(address)
    }

    @TypeConverter
    fun toList(json: String): List<String>? {
        return moshi.adapter<List<String>>(List::class.java).fromJson(json)
    }
}