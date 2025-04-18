package com.images.paging3_compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.images.entity.Article
import com.innovitics.app.shift.core.components.paging.GenericPagerCompose

@Composable
fun NewsScreen() {
    val viewModel = hiltViewModel<NewsViewModel>()
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
