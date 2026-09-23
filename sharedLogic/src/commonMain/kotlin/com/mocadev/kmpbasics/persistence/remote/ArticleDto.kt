package com.mocadev.kmpbasics.persistence.remote

data class ArticleDto(
    val id: Int,
    val title: String,
    val teaser: String,
    val content: String,
)