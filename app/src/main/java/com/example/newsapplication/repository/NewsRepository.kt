package com.example.newsapplication.repository

interface NewsRepository {

    fun getTopHeadlines(callBackListener: CallBackListener)

}