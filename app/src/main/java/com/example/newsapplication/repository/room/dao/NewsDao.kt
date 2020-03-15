package com.example.newsapplication.repository.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapplication.model.News
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("select * from news")
    fun getAll() : Single<List<News>>

    @Insert
    fun addNews(news: News)

    @Insert
    fun addAllNews(list: List<News>)

    @Delete
    fun removeNews(news: News)

    @Query ("select count(*) from news")
    fun getRows() : Int

}