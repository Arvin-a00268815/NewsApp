package com.example.newsapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.model.News
import com.example.newsapplication.ui.main.viewholder.ItemViewHolder
import com.example.newsapplication.ui.main.viewholder.NewsItemViewHolder

class NewsAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val list = ArrayList<News>()

    fun getList() : List<News>{
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.news_article_item, parent, false)
        return NewsItemViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    fun addItem(news: News){
        list.add(news)
    }

    fun addAll(list: List<News>){
        this.list.addAll(list)
    }

    fun getItem(index : Int) : News{
        return list[index]
    }


}

