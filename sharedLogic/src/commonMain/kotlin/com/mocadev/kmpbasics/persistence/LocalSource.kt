package com.mocadev.kmpbasics.persistence

import com.mocadev.kmpbasics.domain.Article
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    fun observeArticles(): Flow<List<Article>>

}