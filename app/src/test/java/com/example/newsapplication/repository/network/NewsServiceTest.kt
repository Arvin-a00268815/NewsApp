package com.example.newsapplication.repository.network

import com.example.newsapplication.TestData
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsServiceTest {

    val mockServer = MockWebServer()

    val newsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Test
    fun `test getTopHeadlines`(){

        mockServer.enqueue(
            MockResponse().setBody(TestData.json).setResponseCode(200)
        )

        val testObserver = newsService.getTopHeadlines().test().values()[0]
        Assert.assertEquals(5, testObserver.articles.size)

    }
}