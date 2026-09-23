package com.mocadev.kmpbasics.persistence.local

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.remote.ArticleDto
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun observeArticles(): Flow<List<ArticleEntity>>
    fun updateArticles(remoteArticles: List<ArticleEntity>)
    fun toggleFavArticle(id: Int)

}