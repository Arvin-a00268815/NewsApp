package com.example.newsapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsapplication.room.DateTimeTypeConverter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class News(

    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @SerializedName("author")
    val author : String?,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val desc : String,
    @SerializedName("url")
    val url : String,
    @SerializedName("urlToImage")
    val urlToImage : String,
    @TypeConverters(DateTimeTypeConverter::class)
    @SerializedName("publishedAt")
    val publishedAt : String,
    @SerializedName("content")
    val content : String?
) : Serializable

