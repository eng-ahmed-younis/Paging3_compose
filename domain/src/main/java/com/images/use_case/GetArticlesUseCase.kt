package com.images.use_case

import androidx.paging.PagingData
import com.images.domain.NewsRepository
import com.images.entity.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
  suspend operator fun invoke(): Flow<PagingData<Article>> {
        return repository.getArticlesPagingSource()
    }
}