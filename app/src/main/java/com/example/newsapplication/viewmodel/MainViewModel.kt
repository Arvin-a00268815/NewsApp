package com.example.newsapplication.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.CallBackListener
import com.example.newsapplication.repository.NewsRepository
import io.reactivex.FlowableSubscriber
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.HttpException


class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    private val topHeadlinesLiveData = MutableLiveData<List<News>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    fun observeIsLoadingLiveData() : LiveData<Boolean>{
        return isLoadingLiveData
    }

    fun getTopHeadlinesLiveData() : LiveData<List<News>>{
        return topHeadlinesLiveData
    }

    fun fetchTopHeadlines() : LiveData<List<News>>{

        val apiObservable = newsRepository.getTopHeadlinesFromApi()
            .doAfterNext {
                compositeDisposable.add(newsRepository.saveNews(it)
                    .subscribeOn(Schedulers.io())
                    .onErrorComplete {
                        true
                    }
                    .subscribe())
            }

        val disposable = Observable.merge(
            newsRepository.getTopHeadlinesFromRoom(),
            apiObservable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoadingLiveData.postValue(true)
            }
            .subscribeWith(object : DisposableObserver<List<News>>(){
                override fun onComplete() {

                    Log.e("c", "-")
                    isLoadingLiveData.postValue(false)
                }

                override fun onNext(t: List<News>) {
//                    Log.e("t", "-"+t.size)

                    topHeadlinesLiveData.postValue(t)
                }

                override fun onError(t: Throwable) {

                    Log.e("e", "-"+t)
                    isLoadingLiveData.postValue(false)
                }

            })
        compositeDisposable.add(disposable)
        return topHeadlinesLiveData

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
