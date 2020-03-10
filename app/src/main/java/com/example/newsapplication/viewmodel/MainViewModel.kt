package com.example.newsapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.Repository
import com.example.newsapplication.repository.network.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: Repository) : ViewModel() {


    private val topHeadlinesLiveData = MutableLiveData<List<News>>()

    fun getTopHeadlinesLiveData() : LiveData<List<News>>{
        return topHeadlinesLiveData
    }

    fun fetchTopHeadlines() : LiveData<List<News>>{
        if(topHeadlinesLiveData.value != null){
            return topHeadlinesLiveData
        }
        repository.getTopHeadlines(object : Repository.ResultsListener{

            override fun onSuccess(articles: List<News>) {
                topHeadlinesLiveData.postValue(articles)
            }

            override fun onError() {

            }



        })


        return topHeadlinesLiveData
    }

}
