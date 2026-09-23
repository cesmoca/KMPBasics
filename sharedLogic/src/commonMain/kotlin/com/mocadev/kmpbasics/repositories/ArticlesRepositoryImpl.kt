package com.mocadev.kmpbasics.repositories

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.local.LocalSource
import com.mocadev.kmpbasics.persistence.remote.RemoteSource
import com.mocadev.kmpbasics.persistence.toDomain
import com.mocadev.kmpbasics.persistence.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticlesRepositoryImpl(
    private val _localSource: LocalSource,
    private val _remoteSource: RemoteSource
) : ArticlesRepository {

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

    override suspend fun toggleFavArticle(id: Int): Boolean {
        return _localSource.toggleFavArticle(id)
    }

    override suspend fun searchArticles(query: String) {
        _localSource.searchArticles(query)
    }

}