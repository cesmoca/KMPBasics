package com.mocadev.kmpbasics.viewmodels

import androidx.lifecycle.ViewModel
import com.mocadev.kmpbasics.repositories.FakeArticlesRepository

class ArticleListViewModel: ViewModel() {

    private val _repository = FakeArticlesRepository()
    val articlesList = _repository.observeArticles()

}