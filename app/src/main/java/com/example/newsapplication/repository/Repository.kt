package com.example.newsapplication.repository

import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.room.AppDatabase
import io.reactivex.*

class Repository (private var newsService: NewsService, private var appDatabase: AppDatabase) : NewsRepository {



    override fun getTopHeadlinesFromRoom() : Observable<List<News>>{
        return appDatabase.newsDao()
            .getAll()
            .toObservable()
    }

    override fun getTopHeadlinesFromApi(): Observable<List<News>> {
        return newsService.getTopHeadlines()
            .toObservable()
            .map { it.articles }
    }

    override fun saveNews(it : List<News>) : Completable{
        return appDatabase.newsDao().addAllNews(it)
    }

}