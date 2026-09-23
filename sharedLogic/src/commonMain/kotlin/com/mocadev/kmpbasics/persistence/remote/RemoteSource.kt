package com.mocadev.kmpbasics.persistence.remote

interface RemoteSource {

    suspend fun refreshArticles(): List<ArticleDto>

}