package com.example.newsapplication.repository

import com.example.newsapplication.model.News
import io.reactivex.rxjava3.disposables.Disposable

interface CallBackListener {

    fun onSuccess(list: List<News>)
    fun onError(code : String, msg : String)
    fun collect(disposable: Disposable?)
}