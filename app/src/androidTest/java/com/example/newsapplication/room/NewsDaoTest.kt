package com.example.newsapplication.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapplication.room.dao.NewsDao
import io.mockk.verify
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
    fun testgetAll(){


        val single = newsDao.getAll()

        Assert.assertTrue( single.test().values()[0].isEmpty() )
        
    }
}