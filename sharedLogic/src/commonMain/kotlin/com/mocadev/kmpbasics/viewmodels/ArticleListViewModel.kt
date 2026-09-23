package com.mocadev.kmpbasics.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocadev.kmpbasics.repositories.ArticlesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArticleListViewModel(private val _repository: ArticlesRepository) : ViewModel() {

    val snackBarMsg = MutableSharedFlow<String>()
    private val _articlesList = _repository
        .observeArticles()
        .catch { emit(null) }
    private val _searchQuery = MutableStateFlow("")
    private val _onlyFavs = MutableStateFlow(false)
    private val _isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        _articlesList, _searchQuery, _onlyFavs, _isRefreshing,
        { articles, searchQuery, onlyFavs, isRefreshing ->
            val safeArticles = articles
                ?: return@combine ArticleListUiState(appState = AppState.Error("App is corrupt"))

            val filteredFavArticles = safeArticles.filter { article ->
                if (!article.isFav && onlyFavs) false
                else true
            }

            ArticleListUiState(
                articlesList = filteredFavArticles,
                searchQuery = searchQuery,
                onlyFavs = onlyFavs,
                isRefreshing = isRefreshing,
                appState = AppState.Normal
            )
        }
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ArticleListUiState()
    )

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .onEach { _repository.searchArticles(it) }
                .collectLatest {}
        }
    }
    sealed interface ArticleListUiEvent {
        data class UpdateSearchQuery(val query: String) : ArticleListUiEvent
        data class ToggleFavArticle(val id: Int) : ArticleListUiEvent
        object ToggleFilterOnlyFav : ArticleListUiEvent
        object RefreshArticles : ArticleListUiEvent
    }

    fun onUiEvent(event: ArticleListUiEvent) {
        when (event) {
            is ArticleListUiEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            is ArticleListUiEvent.ToggleFilterOnlyFav -> toggleOnlyFans()
            is ArticleListUiEvent.RefreshArticles -> refreshArticles()
            is ArticleListUiEvent.ToggleFavArticle -> toggleFavArticle(event.id)
        }
    }

    private fun toggleFavArticle(id: Int) {

        viewModelScope.launch {
            val isFav = _repository.toggleFavArticle(id)

            snackBarMsg.emit(
                if (isFav) "Article marked as fav"
                else "Article removed from favs"
            )
        }

    }

    private fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun toggleOnlyFans() {
        _onlyFavs.value = !_onlyFavs.value

        viewModelScope.launch {
            snackBarMsg.emit(
                if (_onlyFavs.value) "Only favorites shown"
                else "All articles shown"
            )
        }
    }

    private fun refreshArticles() {
        viewModelScope.launch {
            _isRefreshing.value = true

            try {
                snackBarMsg.emit("Refreshing articles...")
                _repository.refreshArticles()
            } catch (e: Exception) {
                e.message?.let {
                    snackBarMsg.emit(it)
                }
            }
            _isRefreshing.value = false
        }
    }


}