package com.images.paging3_compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.images.entity.Article
import com.innovitics.app.shift.core.components.paging.GenericPagerCompose

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<NewsViewModel>()
    //  val articles = viewModel.articles.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.getBreakingNews()
    }


    GenericPagerCompose(
        pagingData = viewModel.articles,
        itemContent = { article ->
            NewsItem(article = article)
        },
        modifier = Modifier.fillMaxSize(),
        loadingContent = {
            LoadingIndicator("Refreshing News...")
        },
        errorContent = { message, onRetry ->
            ErrorItem(message = message, onRetry = onRetry)
        }
    )


    /*  LazyColumn(modifier = modifier) {
        items(articles.itemCount) { article ->
            NewsItem(title = articles[article]?.title ?: "", index = article.toString())
        }

        // Handling refresh state (First Load)
        when (val refreshState = articles.loadState.refresh) {
            is LoadState.Loading -> item { LoadingIndicator("Refreshing News...") }
            is LoadState.Error -> item { ErrorItem(message = refreshState.error.localizedMessage ?: "Unknown Error", onRetry = { articles.retry() }) }
            else -> {}
        }

        // Handling append state (Pagination)
        when (val appendState = articles.loadState.append) {
            is LoadState.Loading -> item { LoadingIndicator("Loading more articles...") }
            is LoadState.Error -> item { ErrorItem(message = appendState.error.localizedMessage ?: "Unknown Error", onRetry = { articles.retry() }) }
            else -> {}
        }
    }
}*/
}
@Composable
fun NewsItem(article: Article) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = article.url ?: "")
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = article.title ?: "")
        Divider(color = Color.Gray)
    }
}

@Composable
fun LoadingIndicator(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, modifier = Modifier.padding(8.dp))
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red, modifier = Modifier.padding(8.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}
