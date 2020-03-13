package com.example.newsapplication.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.dagger.DaggerAppComponent
import com.example.newsapplication.dagger.RetrofitModule
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.viewmodel.MainViewModel
import com.example.newsapplication.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val component = DaggerAppComponent
            .builder().retrofitModule(RetrofitModule("https://newsapi.org"))
            .build()
        component.inject(this)

        val factory = MainViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)


        viewModel.observeEmptyLiveData().observe(viewLifecycleOwner, Observer{ msg ->
            Log.e("called", "--"+msg)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        })
        viewModel.getTopHeadlinesLiveData().observe(viewLifecycleOwner,
            Observer { list ->
                //Log.e("size","="+it.size)
                message.text = list.size.toString()
            })
        message.setOnClickListener {

            viewModel.fetchTopHeadlines()
        }



    }

}
