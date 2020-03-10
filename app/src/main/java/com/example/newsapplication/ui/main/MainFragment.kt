package com.example.newsapplication.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.dagger.DaggerAppComponent
import com.example.newsapplication.dagger.RetrofitModule
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.Repository
import com.example.newsapplication.viewmodel.MainViewModel
import com.example.newsapplication.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var repository: Repository

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

        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        viewModel.getTopHeadlinesLiveData().observe(viewLifecycleOwner,
            Observer<List<News>> {
                //Log.e("size","="+it.size)
                message.text = it.size.toString()
            })
        message.setOnClickListener {

            viewModel.fetchTopHeadlines()
        }



    }

}
