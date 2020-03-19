package com.example.newsapplication.repository

import android.util.Log
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.ErrorResponseHandler
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.room.AppDatabase
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.json.JSONObject
import retrofit2.HttpException

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