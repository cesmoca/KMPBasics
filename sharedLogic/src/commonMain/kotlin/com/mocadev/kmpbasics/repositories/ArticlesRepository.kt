package com.mocadev.kmpbasics.repositories

import com.mocadev.kmpbasics.domain.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    fun observeArticles(): Flow<List<Article>>
    suspend fun refreshArticles()
    fun toggleFavArticle(id: Int): Boolean
}