package com.example.newsapplication.ui.main.viewholder

import android.view.View
import com.example.newsapplication.model.News
import kotlinx.android.synthetic.main.news_article_item.view.*

class NewsItemViewHolder(view: View) : ItemViewHolder(view){

    private val newsTitleTextView = itemView.news_title_textView
    private val newsInfoTextView = itemView.news_info_textView
    private val timeTextView = itemView.news_time_textView

    override fun bindView(news: News) {

        newsTitleTextView.text = news.title
        newsInfoTextView.text = news.desc
        timeTextView.text = news.publishedAt



    }

}