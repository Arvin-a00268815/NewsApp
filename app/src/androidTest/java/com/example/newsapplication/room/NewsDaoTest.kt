package com.example.newsapplication.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapplication.model.News
import com.example.newsapplication.room.dao.NewsDao
import io.mockk.verify
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var newsDao: NewsDao
    lateinit var appDatabase: AppDatabase

    @Before
    fun setUp(){

        appDatabase = Room
            .inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java)
            .build()

        newsDao = appDatabase.newsDao()

    }

    @After
    fun close(){
        appDatabase.close()
    }

    @Test
    fun test_isEmpty(){
        val single = newsDao.getAll().subscribeOn(Schedulers.trampoline())

        single.test().assertValue {
            it.isEmpty()
        }
    }

    @Test
    fun test_getAll(){


        val news = News(1,"JK Rowling", "Harry Potter", "Fiction")
        val news2 = News(2,"JK Rowling", "Harry Potter", "Fiction")

        newsDao.addNews(news).test()
        newsDao.addNews(news2).test()

        val single = newsDao.getAll().subscribeOn(Schedulers.trampoline())

        single.test().assertValue {
            it.size == 2
        }
        //Assert.assertEquals(list, single.test().values()[0])
    }


}