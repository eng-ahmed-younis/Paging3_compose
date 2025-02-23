package com.images.domain

import androidx.paging.PagingData
import com.images.entity.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getArticlesPagingSource(): Flow<PagingData<Article>>
}