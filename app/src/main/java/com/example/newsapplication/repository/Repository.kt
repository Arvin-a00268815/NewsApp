package com.example.newsapplication.repository

import android.util.Log
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.ErrorResponseHandler
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.room.AppDatabase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

class Repository (private var newsService: NewsService, private var appDatabase: AppDatabase) : NewsRepository {


    var isNotEmpty = false

    override fun getTopHeadlines(callBackListener: CallBackListener){

        Log.e("-io-","--")
        if(isNotEmpty){
            Log.e("-i-","--")
            appDatabase.newsDao().getAll()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<News>> {
                    lateinit var disposable: Disposable

                    override fun onSuccess(t: List<News>) {
                        callBackListener.onSuccess(t)
                        disposable.dispose()
                        Log.e("-c-","--")


                    }

                    override fun onSubscribe(d: Disposable) {
                        callBackListener.collect(d)
                        disposable = d
                    }

                    override fun onError(e: Throwable) {
                        Log.e("-e-","--"+e)
                    }


                })

        }else {

            newsService.getTopHeadlines()
                .map { it.articles }
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(object : SingleObserver<List<News>> {
                    var disposable: Disposable? = null
                    override fun onSuccess(t: List<News>) {

                        callBackListener.onSuccess(t)
                            Observable.fromCallable {
                                    appDatabase.newsDao().addAllNews(t)}
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())





                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        callBackListener.collect(d)
                    }

                    override fun onError(e: Throwable) {
                        ErrorResponseHandler.handle(e, callBackListener)
                        disposable?.dispose()
                    }

                })

        }

    }




}