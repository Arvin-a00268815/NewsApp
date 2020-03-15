package com.example.newsapplication.repository.network

import io.reactivex.Single
import retrofit2.http.GET

interface NewsService {


    @GET("/v2/top-headlines?country=us&pageSize=5")
    fun getTopHeadlines() : Single<Response>


}