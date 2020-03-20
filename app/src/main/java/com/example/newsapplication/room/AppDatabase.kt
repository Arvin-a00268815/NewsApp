package com.example.newsapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapplication.model.News
import com.example.newsapplication.room.dao.NewsDao

@Database(entities = [News::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}