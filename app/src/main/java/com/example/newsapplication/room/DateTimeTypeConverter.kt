package com.example.newsapplication.room

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeTypeConverter {
    private val df =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

    @TypeConverter
    fun fromTimestamp(value : String) : Date{
        return df.parse(value)
    }


    @TypeConverter
    fun dateToTimestamp(value : Date) : String {
        return df.format(value)
    }
}