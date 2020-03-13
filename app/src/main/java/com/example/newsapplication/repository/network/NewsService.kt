package com.example.newsapplication.repository.network

import com.example.newsapplication.model.News
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsService {


    @GET("/v2/top-headlines?country=us&pageSize=5")
    fun getTopHeadlines() : Single<Response>


}