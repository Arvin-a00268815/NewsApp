package com.example.newsapplication.ui.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.model.News

abstract class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
    abstract fun bindView(news: News)
}