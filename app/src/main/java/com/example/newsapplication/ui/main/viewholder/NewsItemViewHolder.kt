package com.example.newsapplication.ui.main.viewholder

import android.view.View
import com.example.newsapplication.model.News

class NewsItemViewHolder(view: View) : ItemViewHolder(view){

    private val newsInfoTextView = itemView.news_info_textView
    override fun bindView(news: News) {

        newsInfoTextView.text = news.desc

    }

}