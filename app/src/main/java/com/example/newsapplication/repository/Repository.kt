package com.example.newsapplication.repository

import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.NewsService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository (private var newsService: NewsService) : NewsRepository {


    override fun getTopHeadlines(callBackListener: CallBackListener){


        newsService.getTopHeadlines()
            .map { it.articles }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())

             .subscribe(object : SingleObserver<List<News>>{
                 override fun onSuccess(t: List<News>?) {
                     callBackListener.onSuccess(t!!)
                 }

                 override fun onSubscribe(d: Disposable?) {

                 }

                 override fun onError(e: Throwable?) {

                     callBackListener.onError()
                 }


             })
    }



}