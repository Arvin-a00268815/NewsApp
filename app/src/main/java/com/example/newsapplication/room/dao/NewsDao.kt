package com.example.newsapplication.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapplication.model.News
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("select * from news order by publishedAt desc")
    fun getAll() : Single<List<News>>

    @Insert
    fun addNews(news: News) : Completable

    @Insert
    fun addAllNews(list: List<News>) : Completable

    @Delete
    fun removeNews(news: News) : Completable

    @Query ("select count(*) from news")
    fun getRows() : Single<Int>

}