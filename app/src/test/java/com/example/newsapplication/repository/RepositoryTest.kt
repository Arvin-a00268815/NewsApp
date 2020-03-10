package com.example.newsapplication.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapplication.model.News
import com.example.newsapplication.repository.network.NewsService
import com.example.newsapplication.repository.network.Response
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Callable

class RepositoryTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    lateinit var repository: Repository

    @MockK
    lateinit var newsService: NewsService

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }

        repository = DefaultRepository(newsService)
    }

    @Test
    fun `test getTopHeadlines`(){

        //given
        val news1 = News("author", "title", "desc")
        val list = ArrayList<News>()
        list.add(news1)
        val response = Response("ok", 0, list)
        every {
            newsService.getTopHeadlines()
        } returns  Single.just(response)

        val resultsListener = mockk<CallBackListener>(relaxed = true)


        //when
        repository.getTopHeadlines(resultsListener)

        //then
        val list2 = ArrayList<News>()
        val news2 = News("author", "title", "desc")
        list2.add(news2)

        verify(exactly = 1) { resultsListener.onSuccess(list2) }

        //val testObserver = newsService.getTopHeadlines().map { it.articles }.test().onSuccess(list)
        //testObserver.assertResult(list)

    }
}