package com.example.newsapplication.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.room.dao.NewsDao

@Database(entities = [News::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}