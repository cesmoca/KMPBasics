package com.mocadev.kmpbasics.viewmodels

import com.mocadev.kmpbasics.domain.Article

data class ArticleListUiState(
    val articlesList: List<Article> = emptyList(),
    val searchQuery: String = "",
    val onlyFavs: Boolean = false,
    val isRefreshing: Boolean = false
)