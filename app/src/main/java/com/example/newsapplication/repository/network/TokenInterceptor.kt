package com.example.newsapplication.repository.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            //.addHeader("Authorization", "e720b17142c44c5892f5c428de7dec6d")
            .addHeader("X-Api-Key", "1e720b17142c44c5892f5c428de7dec6d")
            .build()

        return chain.proceed(request)
    }
}