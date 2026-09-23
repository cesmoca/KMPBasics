package com.mocadev.kmpbasics.viewmodels

import com.mocadev.kmpbasics.domain.Article

data class ArticleListUiState(
    val articlesList: List<Article>,
    val searchQuery: String,
    val onlyFavs: Boolean
)