package com.example.newsapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.model.News
import com.example.newsapplication.room.dao.NewsDao

@Database(entities = [News::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}