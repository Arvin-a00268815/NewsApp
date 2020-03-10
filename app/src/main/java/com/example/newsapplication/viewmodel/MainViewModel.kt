package com.example.newsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.CallBackListener
import com.example.newsapplication.repository.DefaultRepository
import com.example.newsapplication.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {


    private val topHeadlinesLiveData = MutableLiveData<List<News>>()

    fun getTopHeadlinesLiveData() : LiveData<List<News>>{
        return topHeadlinesLiveData
    }

    fun fetchTopHeadlines() : LiveData<List<News>>{
        if(topHeadlinesLiveData.value != null){
            return topHeadlinesLiveData
        }
        repository.getTopHeadlines(object : CallBackListener{

            override fun onSuccess(articles: List<News>) {
                topHeadlinesLiveData.postValue(articles)
            }

            override fun onError() {

            }



        })


        return topHeadlinesLiveData
    }

}
