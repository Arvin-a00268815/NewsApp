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

class NewsRepositoryTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var newsRepository: NewsRepository

    @MockK
    lateinit var newsService: NewsService

    @MockK
    lateinit var appDatabase: AppDatabase

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }

        newsRepository = Repository(newsService, appDatabase)
    }

    @Test
    fun `test getTopHeadlinesFromRoom`(){

        //given
                val news1 = News(1,"author", "title", "desc")
        val list = ArrayList<News>()
        list.add(news1)
        every {
            appDatabase.newsDao().getAll()
        } returns Single.just(list)

        val news2 = News(1,"author", "title", "desc")
        val list2 = ArrayList<News>()
        list2.add(news2)
        newsRepository.getTopHeadlinesFromRoom().test().assertResult(list2)
    }
    @Test
    fun `test getTopHeadlinesFromApi`(){

        //given
        val news1 = News(1,"author", "title", "desc")
        val list = ArrayList<News>()
        list.add(news1)
        val response = Response("ok", 0, list)
        val single =Single.just(response)
        every {
            newsService.getTopHeadlines()
        } returns single

        val news2 = News(1,"author", "title", "desc")
        val list2 = ArrayList<News>()
        list2.add(news2)
        val test = newsRepository.getTopHeadlinesFromApi().test()
        test.assertResult(list2)
    }

    @Test
    fun `test saveNews`(){
        val news2 = News(1,"author", "title", "desc")
        val list2 = ArrayList<News>()
        list2.add(news2)

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