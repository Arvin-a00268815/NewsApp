package com.example.newsapplication.repository

import android.util.Log
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.ErrorResponseHandler
import com.example.newsapplication.repository.network.NewsService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

class Repository (private var newsService: NewsService) : NewsRepository {



    override fun getTopHeadlines(callBackListener: CallBackListener){


        newsService.getTopHeadlines()
            .map { it.articles }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<News>>{
                var disposable : Disposable? = null
                override fun onSuccess(t: List<News>?) {
                    callBackListener.onSuccess(t!!)
                    disposable?.dispose()
                }

                override fun onSubscribe(d: Disposable?) {
                    disposable = d
                    callBackListener.collect(d)
                }

                override fun onError(e: Throwable?) {
                    ErrorResponseHandler.handle(e!!, callBackListener)
                    disposable?.dispose()
                }

            })


    }




}