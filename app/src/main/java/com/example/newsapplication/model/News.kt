package com.example.newsapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    @SerializedName("author")
    val author : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val desc : String) : Serializable