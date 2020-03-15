package com.example.newsapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class News(

    @PrimaryKey
    val id : Int,

    @SerializedName("author")
    val author : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val desc : String) : Serializable