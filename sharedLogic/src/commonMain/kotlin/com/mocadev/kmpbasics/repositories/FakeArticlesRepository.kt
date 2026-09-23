package com.mocadev.kmpbasics.repositories

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.local.FakeLocalSource
import com.mocadev.kmpbasics.persistence.remote.FakeRemoteSource
import com.mocadev.kmpbasics.persistence.toDomain
import com.mocadev.kmpbasics.persistence.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeArticlesRepository : ArticlesRepository {

    private val _localSource = FakeLocalSource()
    private val _remoteSource = FakeRemoteSource()

    override fun observeArticles(): Flow<List<Article>?> =
        _localSource.observeArticles().map { list ->
            list.map { article -> article.toDomain() }
        }

    override suspend fun refreshArticles() {
        val remoteArticles = _remoteSource.refreshArticles()
        _localSource.updateArticles(
            remoteArticles.map {
                it.toDomain().toEntity()
            }
        )
    }

    override fun toggleFavArticle(id: Int): Boolean {
        return _localSource.toggleFavArticle(id)
    }

}