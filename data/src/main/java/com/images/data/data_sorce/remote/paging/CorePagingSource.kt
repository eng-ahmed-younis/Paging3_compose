package com.images.data.data_sorce.remote.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * CorePagingSource is a generic implementation of PagingSource to handle paginated data
 * fetching consistently across different data sources.
 *
 * @param T The type of data being loaded.
 * @param Query The type of query or request parameter used to fetch the data.
 * @param fetch Function to perform the data fetching given the page number and query.
 * @param onError Function to handle errors that occur during data fetching.
 */
abstract class CorePagingSource<T : Any, Query : Any>(
    private val initialPage: Int = 1,
    //pass an optional query or filter parameter to the PagingSource.
    // such as search keywords, filter criteria, or other dynamic data.
    private val query: Query? = null,
    private val fetch: suspend (page: Int, query: Query?) -> List<T>,
    private val onError: ((Throwable) -> Unit)? = null
) : PagingSource<Int, T>() {

    /**
     * Provides a key for refreshing data in the PagingSource.
     *
     * This method is called when the Paging library needs to reload data,
     * for example, when the user performs a swipe-to-refresh action.
     * It calculates the next page key based on the position of the anchor.
     *
     * @param state The current state of the PagingSource, containing information about
     * the currently loaded pages and the user's scroll position.
     * @return The key for the page to load next, or null if the key cannot be determined.
     */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {


        // Get the anchor position, which is the most recently accessed index in the list.
        // This is typically the item currently visible to the user.
        return state.anchorPosition?.let { anchorPosition ->

            // anchor position current item visible and scroll
            // Find the page that is closest to the anchor position.
            // The closest page helps determine whether the user is scrolling up or down.
            val closestPage = state.closestPageToPosition(anchorPosition)

            // Determine the key for refreshing the data:
            // - If the closest page has a previous key, add 1 to it. This means the user is likely
            //   scrolling forward, so we load the next page.
            // - If the closest page has a next key, subtract 1 from it. This indicates the user is
            //   scrolling backward, so we load the previous page.
            // - If neither is available, null is returned, indicating no refresh key is needed.
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }


    /**
     * Main function to load data asynchronously.
     * Calls the fetch function and handles errors if they occur.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: initialPage
        return try {
            val data = fetch(page, query)
            LoadResult.Page(
                data = data,
                prevKey = if (page == initialPage) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            onError?.invoke(e)
            LoadResult.Error(e)
        }
    }
}
