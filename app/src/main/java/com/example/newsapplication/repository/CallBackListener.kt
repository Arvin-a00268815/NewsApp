package com.example.newsapplication.repository

import com.example.newsapplication.model.News
import io.reactivex.disposables.Disposable

interface CallBackListener {

    fun onSuccess(list: List<News>)
    fun onError(code : String, msg : String)
    fun collect(disposable: Disposable)
}