package com.example.newsapplication.repository.network

import com.example.newsapplication.model.News
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsService {


    @GET("/v2/top-headlines?country=us&pageSize=5&apiKey=e720b17142c44c5892f5c428de7dec6d")
    fun getTopHeadlines() : Single<Response>


}