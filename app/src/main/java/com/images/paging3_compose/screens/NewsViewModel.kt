package com.images.paging3_compose.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.images.di.IO_DISPATCHER
import com.images.entity.Article
import com.images.use_case.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher

): ViewModel() {

    private val article = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles = article.asStateFlow()


    fun getBreakingNews() {
        viewModelScope.launch(ioDispatcher) {
            getArticlesUseCase().collect { pagingData ->
                article.value = pagingData
            }
        }
    }
}