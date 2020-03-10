package com.example.newsapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.Repository
import com.example.newsapplication.repository.network.Response
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Callable


class ViewModelTest {



    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @InjectMockKs
    lateinit var viewModel: MainViewModel

    @MockK(relaxed = true)
    lateinit var repository: Repository




    @Before
    fun setup(){
        MockKAnnotations.init(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `test fetchTopHeadlines`(){

        val news = News("author", "title", "desc")
        val list = ArrayList<News>()
        list.add(news)

        val observer = mockk<Observer<List<News>>>(relaxed = true)

        viewModel.fetchTopHeadlines().observeForever(observer)

        every { repository.getTopHeadlines(any()) } answers {
            val resultsListener = it.invocation.args[0] as Repository.ResultsListener
            val response = Response("ok", 0, list)
            resultsListener.onSuccess(response.articles)
        }

        viewModel.fetchTopHeadlines()
        val news1 = News("author", "title", "desc")
        val expectedList = ArrayList<News>()
        expectedList.add(news1)

        verify(exactly = 1) { observer.onChanged(expectedList) }

    }
}