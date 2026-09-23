package com.mocadev.kmpbasics.persistence.local

data class ArticleEntity(
    val id: Int,
    val title: String,
    val teaser: String,
    val content: String,
    var isFav: Boolean,
)