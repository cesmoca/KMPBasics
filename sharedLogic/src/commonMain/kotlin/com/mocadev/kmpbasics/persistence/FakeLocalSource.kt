package com.mocadev.kmpbasics.persistence

import com.mocadev.kmpbasics.domain.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalSource: LocalSource {

    private val sampleArticles = listOf(
        Article(1, "Article 1", "Teaser 1", "Content 1..."),
        Article(2, "Article 2", "Teaser 2", "Content 2..."),
        Article(3, "Article 3", "Teaser 3", "Content 3..."),
    )

    override fun observeArticles(): Flow<List<Article>> = flow {
    }
}