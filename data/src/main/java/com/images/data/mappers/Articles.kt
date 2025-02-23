package com.images.data.mappers

import com.images.data.dto.ArticleDto
import com.images.entity.Article
import com.images.entity.Source

fun List<ArticleDto>.toArticleList(): List<Article>  {
    return this.map {
        Article(
            author = it.author ?: "",
            content = it.content,
            description = it.description,
            title = it.title,
            url = it.url
        )
    }
}