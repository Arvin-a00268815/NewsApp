package com.example.newsapplication.repository

import android.util.Log
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.ErrorResponseHandler
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.room.AppDatabase
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

class Repository (private var newsService: NewsService, private var appDatabase: AppDatabase) : NewsRepository {



    override fun getTopHeadlines(callBackListener: CallBackListener){


        appDatabase.newsDao().getAll()
                .subscribeOn(Schedulers.io())
                .doAfterSuccess {
                    newsService.getTopHeadlines()
                        .map {
                            it.articles
                        }
                        .doAfterSuccess {


                            Log.e("success", "success")
                            appDatabase.newsDao().addAllNews(it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())

                                .subscribe {

                                    Log.e("complete", "--")
                                }



                        }
                        .subscribeOn(Schedulers.io())
                        .subscribe(object : SingleObserver<List<News>> {
                            var disposable: Disposable? = null


                            override fun onSubscribe(d: Disposable) {
                                disposable = d
                                callBackListener.collect(d)
                            }

                            override fun onError(e: Throwable) {
                                ErrorResponseHandler.handle(e, callBackListener)
                                disposable?.dispose()
                            }

                            override fun onSuccess(t: List<News>) {
                                callBackListener.onSuccess(t)
                            }

                        })

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<News>> {
                    lateinit var disposable: Disposable

                    override fun onSubscribe(d: Disposable) {
                        callBackListener.collect(d)
                        disposable = d
                    }

                    override fun onError(e: Throwable) {
                        Log.e("-e-","--"+e)
                        disposable.dispose()
                    }

                    override fun onSuccess(t: List<News>) {
                        callBackListener.onSuccess(t)
                        Log.e("--","--"+t.size)
                    }

                })


    }






}