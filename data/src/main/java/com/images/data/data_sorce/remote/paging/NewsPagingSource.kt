package com.images.data.data_sorce.remote.paging

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.images.data.data_sorce.remote.service.NewsServiceApi
import com.images.data.dto.ArticleDto
import com.images.data.mappers.toArticleList
import com.images.entity.Article
import javax.inject.Inject


class NewsPagingSource @Inject constructor(
    private val newsServiceApi: NewsServiceApi

) : CorePagingSource<Article, Unit>(
    initialPage = 1,
    query = Unit,
    onError = { error ->
        println("Error fetching news: ${error.message}")
    },
    fetch = { page, _ ->
        val response = newsServiceApi.getEverythingNews(page = page, pageSize = 20)
        if (response.status == "ok") {
            response.articles.toArticleList()
        } else {
            throw Exception("API Error: ${response.status}")
        }
    }
)

/*
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

class NewsPagingSource @Inject constructor(
    private val newsServiceApi: NewsServiceApi
) : PagingSource<Int, Article>() {
    */
/**
 * The [PagingState] contains information about the current state of the data being loaded,
 * including a list of pages that have already been loaded (pages), and it also keeps track
 * of the anchor position (the current position of the item the user is interacting with
 * in the list).
 *
 * The closest page refers to:
 *
 * [The page closest] to the anchor position: The current position in the list.
 * This is important when refreshing or calculating which page should be loaded next or previous.
 * The closestPageToPosition() function helps in calculating pagination keys,
 * as it provides information on the current page relative to the anchor position
 * (whether the user is scrolling up or down).
 *
 * *//*

    // Function to determine the key for refreshing the data
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // Find the closest page to the anchor position, and return the key for it.
            // If the closest page has a `prevKey`, return it incremented by 1 (as we're going backward).
            // If the closest page has a `nextKey`, return it decremented by 1 (as we're going forward).
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Main function to load data from the API (called by Paging library)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            // If there's no key, set the initial page to 1
            val page = params.key ?: 1

            // Fetch data from the API using the page number
            val response = newsServiceApi.getEverythingNews(
                page = page,
                pageSize = params.loadSize
            )

            // Safely handle the articles list, ensuring it's never null
            val articles = response.articles.toArticleList() ?: emptyList()

            // Return the data wrapped in LoadResult.Page for Paging library to consume
            if (response.status == "ok") {
                LoadResult.Page(
                    data = articles,  // The list of articles to display
                    prevKey = if (page == 1) null else page - 1, // If the current page is 1, there's no previous page
                    nextKey = if (articles.isEmpty()) null else page + 1, // If no articles, there's no next page
                )
            } else{
                LoadResult.Error(Exception("API Error: ${response.status}"))
            }
        } catch (e: Exception) {
            // If an error occurs (network issues, server errors, etc.), return LoadResult.Error
            LoadResult.Error(e)
        }
    }


}*/
