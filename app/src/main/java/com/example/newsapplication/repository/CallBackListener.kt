package com.example.newsapplication.repository

import com.example.newsapplication.model.News

interface CallBackListener {

    fun onSuccess(list: List<News>)
    fun onError()
}