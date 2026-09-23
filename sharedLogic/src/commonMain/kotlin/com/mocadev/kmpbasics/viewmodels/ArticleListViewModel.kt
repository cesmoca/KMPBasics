package com.mocadev.kmpbasics.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocadev.kmpbasics.repositories.FakeArticlesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ArticleListViewModel: ViewModel() {

    private val _repository = FakeArticlesRepository()
    private val _articlesList = _repository.observeArticles()
    private val _searchQuery = MutableStateFlow("")
    private val _onlyFavs = MutableStateFlow(false)

    val uiState = combine(
        _articlesList, _searchQuery, _onlyFavs,
        { articles, searchQuery, onlyFavs ->
            ArticleListUiState(
                articlesList = articles,
                searchQuery = searchQuery,
                onlyFavs = onlyFavs
            )
        }
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ArticleListUiState()
    )

    sealed interface ArticleListUiEvent{
        data class UpdateSearchQuery(val query: String): ArticleListUiEvent
        object ToggleOnlyFav: ArticleListUiEvent
    }

    fun onUiEvent(event: ArticleListUiEvent){
        when(event){
            is ArticleListUiEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            is ArticleListUiEvent.ToggleOnlyFav -> toggleOnlyFans()
        }
    }

    private fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    private fun toggleOnlyFans(){
        _onlyFavs.value = !_onlyFavs.value
    }
}