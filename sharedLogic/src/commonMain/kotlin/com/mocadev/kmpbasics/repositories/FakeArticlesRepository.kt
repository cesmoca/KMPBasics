package com.mocadev.kmpbasics.repositories

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.FakeLocalSource
import com.mocadev.kmpbasics.persistence.FakeRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeArticlesRepository: ArticlesRepository {

    private val _localSource = FakeLocalSource()
    private val _remoteSource = FakeRemoteSource()

    val articlesList = MutableStateFlow(listOf<Article>())

    override fun observeArticles(): Flow<List<Article>> = _localSource.observeArticles()
    override fun openArticle(id: Int): Article? = null
    override fun refreshArticles() = _remoteSource.refreshArticles()

}