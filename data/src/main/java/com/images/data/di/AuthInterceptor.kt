package com.images.data.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("X-Api-Key","b94ca5c7a9974abbbd1ea53b619c9c89")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}