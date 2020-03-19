package com.example.newsapplication.repository

import com.example.newsapplication.model.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface NewsRepository {



    fun getTopHeadlinesFromRoom() : Observable<List<News>>

    fun getTopHeadlinesFromApi() : Observable<List<News>>

    fun saveNews(list: List<News>) : Completable

}