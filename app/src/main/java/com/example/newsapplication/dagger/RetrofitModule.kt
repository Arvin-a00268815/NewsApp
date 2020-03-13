package com.example.newsapplication.dagger

import com.example.newsapplication.repository.Repository
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.network.TokenInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModule(private var url : String) {


    @Provides
    @Singleton
    fun provideRetrofitInstance(gson: Gson, okHttpClient: OkHttpClient) : Retrofit{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    fun provideDefaultRepository(newsService: NewsService) : NewsRepository{
        return Repository(newsService)
    }
}