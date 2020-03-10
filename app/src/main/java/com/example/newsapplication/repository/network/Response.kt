package com.example.newsapplication.repository.network

import com.example.newsapplication.model.News
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Response (@SerializedName("status")
                val status : String,
                @SerializedName("totalResults")
                val totalResults : Int,
                @SerializedName("articles")
                val articles : List<News>) : Serializable