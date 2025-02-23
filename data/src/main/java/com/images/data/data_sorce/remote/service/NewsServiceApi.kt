package com.images.data.data_sorce.remote.service

import com.images.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServiceApi {

    @GET("everything")
    suspend fun getEverythingNews(
        @Query("q") q: String = "gold",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int
    ): NewsResponse
}

