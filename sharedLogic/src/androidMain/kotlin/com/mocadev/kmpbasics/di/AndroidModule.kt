package com.mocadev.kmpbasics.di

import com.mocadev.kmpbasics.persistence.local.FakeLocalSource
import com.mocadev.kmpbasics.persistence.local.LocalSource
import com.mocadev.kmpbasics.persistence.remote.FakeRemoteSource
import com.mocadev.kmpbasics.persistence.remote.RemoteSource
import com.mocadev.kmpbasics.viewmodels.ArticleListViewModel
import org.koin.dsl.module

val androidModule = module{

    single<LocalSource>{
        FakeLocalSource()
    }

    single<RemoteSource>{
        FakeRemoteSource()
    }
}