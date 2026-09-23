package com.mocadev.kmpbasics.di

import com.mocadev.kmpbasics.persistence.local.LocalSource
import com.mocadev.kmpbasics.persistence.remote.RemoteSource
import com.mocadev.kmpbasics.repositories.ArticlesRepository
import com.mocadev.kmpbasics.repositories.ArticlesRepositoryImpl
import com.mocadev.kmpbasics.viewmodels.ArticleListViewModel
import org.koin.dsl.module

val commonModule = module {

    single<ArticlesRepository>{
        ArticlesRepositoryImpl(get(), get())
    }

    factory{
        ArticleListViewModel(
            _repository = get()
        )
    }
}