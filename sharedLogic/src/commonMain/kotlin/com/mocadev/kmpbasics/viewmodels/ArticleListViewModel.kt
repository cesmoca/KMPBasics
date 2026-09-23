package com.mocadev.kmpbasics.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocadev.kmpbasics.repositories.FakeArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArticleListViewModel: ViewModel() {

    val snackBarMsg = MutableSharedFlow<String>()

    private val _repository = FakeArticlesRepository()
    private val _articlesList = _repository.observeArticles()
    private val _searchQuery = MutableStateFlow("")
    private val _onlyFavs = MutableStateFlow(false)

    private val _isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        _articlesList, _searchQuery, _onlyFavs, _isRefreshing,
        { articles, searchQuery, onlyFavs, isRefreshing ->
            val filteredArticles = articles.filter { article ->
                article.title.contains(searchQuery, ignoreCase = true) ||
                        article.teaser.contains(searchQuery, ignoreCase = true) ||
                        article.content.contains(searchQuery, ignoreCase = true)
            }.filter { article ->
                if(!article.isFav && onlyFavs) false
                else true
            }
            ArticleListUiState(
                articlesList = filteredArticles,
                searchQuery = searchQuery,
                onlyFavs = onlyFavs,
                isRefreshing = isRefreshing
            )
        }
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ArticleListUiState()
    )

    sealed interface ArticleListUiEvent{
        data class UpdateSearchQuery(val query: String): ArticleListUiEvent
        data class ToggleFavArticle(val id: Int): ArticleListUiEvent
        object ToggleFilterOnlyFav: ArticleListUiEvent
        object RefreshArticles: ArticleListUiEvent
    }

    fun onUiEvent(event: ArticleListUiEvent){
        when(event){
            is ArticleListUiEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            is ArticleListUiEvent.ToggleFilterOnlyFav -> toggleOnlyFans()
            is ArticleListUiEvent.RefreshArticles -> refreshArticles()
            is ArticleListUiEvent.ToggleFavArticle -> toggleFavArticle(event.id)
        }
    }

    private fun toggleFavArticle(id: Int) {

        val isFav = _repository.toggleFavArticle(id)

        viewModelScope.launch {
            snackBarMsg.emit(
                if (isFav) "Article marked as fav"
                else "Article removed from favs"
            )
        }

    }

    private fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    private fun toggleOnlyFans(){
        _onlyFavs.value = !_onlyFavs.value

        viewModelScope.launch {
            snackBarMsg.emit(
                if (_onlyFavs.value) "Only favorites shown"
                else "All articles shown"
            )
        }
    }

    private fun refreshArticles(){
        viewModelScope.launch {
            _isRefreshing.value = true

            try {
                snackBarMsg.emit("Refreshing articles...")
                _repository.refreshArticles()
            }catch (e: Exception){
                e.message?.let{
                    snackBarMsg.emit(it)
                }
            }
            _isRefreshing.value = false
        }
    }


}