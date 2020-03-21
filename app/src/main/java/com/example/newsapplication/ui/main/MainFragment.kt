package com.example.newsapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.dagger.DaggerAppComponent
import com.example.newsapplication.dagger.RetrofitModule
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
            .builder()
            .retrofitModule(RetrofitModule("https://newsapi.org"))
            .build()
        component.inject(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NewsAdapter()
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            RecyclerView.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val factory = MainViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)


        viewModel.observeEmptyLiveData().observe(viewLifecycleOwner, Observer{ msg ->
            Log.e("called", "--"+msg)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        })
        viewModel.getTopHeadlinesLiveData().observe(viewLifecycleOwner,
            Observer { list ->

                adapter.addAll(list)
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "size=="+list.size.toString(), Toast.LENGTH_LONG).show()
            })


        viewModel.fetchTopHeadlines()


    }

}
