package com.example.newsapplication.dagger

import com.example.newsapplication.repository.Repository
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.network.TokenInterceptor
import com.example.newsapplication.room.AppDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModule(private var url : String) {


    @Provides
    @Singleton
    fun provideRetrofitInstance(gson: Gson, okHttpClient: OkHttpClient) : Retrofit{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(url)
        return retrofit.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(TokenInterceptor())
        return okHttpClient.build()
    }
//
//    @Provides
//    @Singleton
//    fun provideErrorHandlingInterceptor() : ErrorHandlingInterceptor{
//
//    }


    @Provides
    @Singleton
    fun provideGson() : Gson{
        val gson = Gson()
        return gson
    }

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit) : NewsService{
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideDefaultRepository(newsService: NewsService, appDatabase: AppDatabase) : NewsRepository{
        return Repository(newsService, appDatabase)
    }
}