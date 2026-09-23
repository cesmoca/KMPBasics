package com.mocadev.kmpbasics.persistence

import com.mocadev.kmpbasics.domain.Article

class FakeRemoteSource: RemoteSource {

    private val sampleArticles = listOf(
        Article(1, "Article 1", "Teaser 1", "Content 1..."),
        Article(2, "Article 2", "Teaser 2", "Content 2..."),
        Article(3, "Article 3", "Teaser 3", "Content 3..."),
        Article(4, "Article 4", "Teaser 4", "Content 4..."),
        Article(5, "Article 5", "Teaser 5", "Content 5..."),
        Article(6, "Article 6", "Teaser 6", "Content 6..."),
    )

    override fun refreshArticles() {
    }

}