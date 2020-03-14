package com.example.newsapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.CallBackListener
import com.example.newsapplication.repository.NewsRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.HttpException


class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    private val topHeadlinesLiveData = MutableLiveData<List<News>>()
    private val emptyLiveData = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun observeEmptyLiveData() : LiveData<String>{
        return emptyLiveData
    }

    fun getTopHeadlinesLiveData() : LiveData<List<News>>{
        return topHeadlinesLiveData
    }

    fun fetchTopHeadlines() : LiveData<List<News>>{
        if(topHeadlinesLiveData.value != null){
            return topHeadlinesLiveData
        }
        newsRepository.getTopHeadlines(object : CallBackListener{

            override fun onSuccess(articles: List<News>) {
                topHeadlinesLiveData.postValue(articles)
            }

            override fun onError(code: String, msg: String) {
                emptyLiveData.postValue(msg)
            }

            override fun collect(disposable: Disposable?) {
                compositeDisposable.add(disposable)
            }


        })


        return topHeadlinesLiveData
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
