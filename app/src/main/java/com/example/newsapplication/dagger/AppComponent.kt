package com.example.newsapplication.dagger

import com.example.newsapplication.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(fragment: MainFragment)
}