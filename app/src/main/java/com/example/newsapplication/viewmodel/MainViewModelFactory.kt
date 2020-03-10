package com.example.newsapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.repository.DefaultRepository
import com.example.newsapplication.repository.Repository

class MainViewModelFactory(var repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}