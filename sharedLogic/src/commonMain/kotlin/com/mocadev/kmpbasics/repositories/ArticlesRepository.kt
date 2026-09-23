package com.mocadev.kmpbasics.repositories

import com.mocadev.kmpbasics.domain.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    fun observeArticles(): Flow<List<Article>>
    fun refreshArticles()
}