package com.example.newsapplication.repository

import com.example.newsapplication.model.News

interface Repository {

    fun getTopHeadlines(callBackListener: CallBackListener)

}