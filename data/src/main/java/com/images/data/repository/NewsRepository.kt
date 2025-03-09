package com.images.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.images.data.data_sorce.remote.paging.NewsPagingSource
import com.images.data.data_sorce.remote.service.NewsServiceApi
import com.images.domain.NewsRepository
import com.images.entity.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class NewsRepositoryImpl @Inject constructor(private val newsApiService: NewsServiceApi) : NewsRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getArticlesPagingSource() : Flow<PagingData<Article>> {
        /**
         * When the Pager needs to load data, it invokes the [pagingSourceFactory],
         * which creates a [NewsPagingSource] instance.
         * */
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsApiService) }
        ).flow
    }
}
