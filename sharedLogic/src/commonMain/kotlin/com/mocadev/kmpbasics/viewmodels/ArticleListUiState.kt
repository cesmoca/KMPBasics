package com.mocadev.kmpbasics.viewmodels

import com.mocadev.kmpbasics.domain.Article


sealed interface AppState{
    object Normal: AppState
    object Loading: AppState
    data class Error(val msg: String): AppState
}

data class ArticleListUiState(
    val articlesList: List<Article> = emptyList(),
    val searchQuery: String = "",
    val onlyFavs: Boolean = false,
    val isRefreshing: Boolean = false,
    val appState: AppState = AppState.Loading
)