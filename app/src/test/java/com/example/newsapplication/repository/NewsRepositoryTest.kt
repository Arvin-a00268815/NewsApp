package com.example.newsapplication.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.network.Response
import com.example.newsapplication.room.AppDatabase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class NewsRepositoryTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var newsRepository: NewsRepository

    @MockK
    lateinit var newsService: NewsService

    @MockK
    lateinit var appDatabase: AppDatabase

    lateinit var news : News

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }

        newsRepository = Repository(newsService, appDatabase)
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
    fun `test getTopHeadlinesFromRoom`(){

        //given
        val list = ArrayList<News>()
        list.add(news)
        every {
            appDatabase.newsDao().getAll()
        } returns Single.just(list)

        val list2 = ArrayList<News>()
        list2.add(news)
        newsRepository.getTopHeadlinesFromRoom().test().assertResult(list2)
    }
    @Test
    fun `test getTopHeadlinesFromApi`(){

        //given
        val list = ArrayList<News>()
        list.add(news)
        val response = Response("ok", 0, list)
        val single =Single.just(response)
        every {
            newsService.getTopHeadlines()
        } returns single

        val test = newsRepository.getTopHeadlinesFromApi().test()
        test.assertResult(list)
    }

    @Test
    fun `test saveNews`(){
        val list2 = ArrayList<News>()
        list2.add(news)

        every {
            appDatabase.newsDao().addAllNews(list2)
        } returns Completable.complete()

        val test = newsRepository.saveNews(list2).test()
        test.assertComplete()

    }
//    @Test
//    fun `test getTopHeadlines`(){
//
//        //given
//        val news1 = News(1,"author", "title", "desc")
//        val list = ArrayList<News>()
//        list.add(news1)
//        val response = Response("ok", 0, list)
//        every {
//            newsService.getTopHeadlines()
//        } returns  Single.just(response)
//
//        val resultsListener = mockk<CallBackListener>(relaxed = true)
//
//
//        //when
//        newsRepository.getTopHeadlines(resultsListener)
//
//        //then
//        val list2 = ArrayList<News>()
//        val news2 = News(1,"author", "title", "desc")
//        list2.add(news2)
//
//        verify(exactly = 1) { resultsListener.onSuccess(list2) }
//
//        //val testObserver = newsService.getTopHeadlines().map { it.articles }.test().onSuccess(list)
//        //testObserver.assertResult(list)
//
//    }
}