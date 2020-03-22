package com.example.newsapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.CallBackListener
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.repository.Repository
import com.example.newsapplication.repository.network.Response
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList


class ViewModelTest {



    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @InjectMockKs
    lateinit var viewModel: MainViewModel

    @MockK(relaxed = true)
    lateinit var repository: NewsRepository

    lateinit var news : News

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        viewModel = MainViewModel(repository)

        news = News("Corinne Heller",
            "Taylor Swift and Kanye West's ''Famous'' Phone Call Leaks Online - E! NEWS",
            "Extended video footage of what is believed to be the infamous phone call between Kanye West and Taylor Swift has gone viral on Twitter, four years after the pair reignited their feud.",
            "https://www.eonline.com/news/1133198/taylor-swift-and-kanye-west-s-famous-phone-call-leaks-online" ,
            "https://www.eonline.com/news/1133198/taylor-swift-and-kanye-west-s-famous-phone-call-leaks-online",
             Date(),
            "During the call, Kanye asks Taylor to tweet about his new single, which he says includes a \"very controversial line\" about her at the beginning of the song. She asks what the lyric is and asks if it's going to be \"mean.\"\r\n\"No, I don't think it's mean,\" Kanye … [+2005 chars]"
        )

    }

    @Test
    fun `test fetchTopHeadlines check api`(){
        val list = ArrayList<News>()
        list.add(news)

        val observer = mockk<Observer<List<News>>>(relaxed = true)

        viewModel.getTopHeadlinesLiveData().observeForever(observer)

        every { repository.getTopHeadlinesFromApi() } returns Observable.just(list)

        viewModel.fetchTopHeadlines()

        verify(exactly = 1) { observer.onChanged(list) }

    }

    @Test
    fun `test fetchTopHeadlines check Room`(){

        val list = ArrayList<News>()
        list.add(news)

        val observer = mockk<Observer<List<News>>>(relaxed = true)

        viewModel.getTopHeadlinesLiveData().observeForever(observer)

        every { repository.getTopHeadlinesFromRoom() } returns Observable.just(list)

        viewModel.fetchTopHeadlines()
        val expectedList = ArrayList<News>()
        expectedList.add(news)

        verify(exactly = 1) { observer.onChanged(expectedList) }

    }

    @Test
    fun `test fetchTopHeadlines check Room & Api`(){
        val list = ArrayList<News>()
        list.add(news)

        val observer = mockk<Observer<List<News>>>(relaxed = true)

        viewModel.getTopHeadlinesLiveData().observeForever(observer)

        every { repository.getTopHeadlinesFromRoom() } returns Observable.just(list)
        every { repository.getTopHeadlinesFromApi() } returns Observable.just(list)

        viewModel.fetchTopHeadlines()
        val expectedList = ArrayList<News>()
        expectedList.add(news)

        verify (exactly = 2) { observer.onChanged(expectedList) }

    }

    @Test
    fun `test fetchTopHeadlines check Room & Api & saveList`(){
        val list = ArrayList<News>()
        list.add(news)

        val observer = mockk<Observer<List<News>>>(relaxed = true)

        viewModel.getTopHeadlinesLiveData().observeForever(observer)

        every { repository.getTopHeadlinesFromApi() } returns Observable.just(list)

        viewModel.fetchTopHeadlines()
        val expectedList = ArrayList<News>()
        expectedList.add(news)

        verify (exactly = 1) { repository.saveNews(expectedList) }

    }

//    @Test
//    fun `test fetchTopHeadlines`(){
//
//        val news = News(1, "author", "title", "desc")
//        val list = ArrayList<News>()
//        list.add(news)
//
//        val observer = mockk<Observer<List<News>>>(relaxed = true)
//
//        viewModel.fetchTopHeadlines().observeForever(observer)
//
//        every { repository.getTopHeadlines(any()) } answers {
//            val resultsListener = it.invocation.args[0] as CallBackListener
//            val response = Response("ok", 0, list)
//            resultsListener.onSuccess(response.articles)
//        }
//
//        viewModel.fetchTopHeadlines()
//        val news1 = News(1,"author", "title", "desc")
//        val expectedList = ArrayList<News>()
//        expectedList.add(news1)
//
//        verify(exactly = 1) { observer.onChanged(expectedList) }
//
//    }
}