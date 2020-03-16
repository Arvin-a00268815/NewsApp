package com.example.newsapplication.dagger

import android.content.Context
import androidx.room.Room
import com.example.newsapplication.MainActivity
import com.example.newsapplication.repository.room.AppDatabase
import com.example.newsapplication.ui.main.MainFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    fun providesContext(): Context {
        return MainActivity.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "news-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}