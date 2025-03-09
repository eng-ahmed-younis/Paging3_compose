package com.innovitics.app.shift.core.components.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

/**
 * A fully generic pager using Paging 3 and Jetpack Compose that supports vertical scrolling
 * and optional content for [loading], [error], and [empty] states.
 *
 * This composable is designed to display paginated lists in a clean and reusable manner.
 * It handles loading, error, and empty states through optional composable functions,
 * allowing for a high degree of customization.
 *
 * @param T The type of items in the list.
 * @param pagingData A flow of PagingData to be displayed.
 * @param itemContent A composable function to render each item in the list.
 * @param modifier Modifier for applying additional UI configurations.
 * @param loadingContent Optional composable function to display when data is loading.
 * @param errorContent Optional composable function to display when an error occurs with a retry option.
 * @param emptyContent Optional composable function to display when the list is empty.
 */
@Composable
fun <T : Any> GenericPagerCompose(
    pagingData: Flow<PagingData<T>>,
    itemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    loadingContent: (@Composable () -> Unit)? = null,
    errorContent: (@Composable (String, () -> Unit) -> Unit)? = null,
    emptyContent: (@Composable () -> Unit)? = null
) {
    val lazyPagingItems: LazyPagingItems<T> = pagingData.collectAsLazyPagingItems()

    Box(modifier = modifier) {
        when {
            // Display loading content during the first load
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                loadingContent?.invoke()
            }

            // Display error during the first load with retry option
            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                val error = lazyPagingItems.loadState.refresh as LoadState.Error
                errorContent?.invoke(error.error.localizedMessage ?: "Unknown Error") {
                    lazyPagingItems.retry()
                }
            }

            // Display empty content if the list is empty
            lazyPagingItems.itemCount == 0 -> {
                emptyContent?.invoke()
            }

            // Display the loaded items with proper handling for pagination
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // Display each item using the provided itemContent
                    items(lazyPagingItems.itemCount) { index ->
                        lazyPagingItems[index]?.let { item ->
                            itemContent(item)
                        }
                    }

                    // Handling refresh state (First Load)
                    when (val refreshState = lazyPagingItems.loadState.refresh) {
                        is LoadState.Loading -> item {
                            loadingContent?.invoke()
                        }
                        is LoadState.Error -> item {
                            errorContent?.invoke(
                                refreshState.error.localizedMessage ?: "Unknown Error"
                            ) {
                                lazyPagingItems.retry()
                            }
                        }
                        else -> {}
                    }

                    // Handling append state (Pagination)
                    when (val appendState = lazyPagingItems.loadState.append) {
                        is LoadState.Loading -> item {
                            loadingContent?.invoke()
                        }
                        is LoadState.Error -> item {
                            errorContent?.invoke(
                                appendState.error.localizedMessage ?: "Unknown Error"
                            ) {
                                lazyPagingItems.retry()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
