package com.mocadev.kmpbasics.persistence

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.local.ArticleEntity
import com.mocadev.kmpbasics.persistence.remote.ArticleDto

fun ArticleDto.toDomain() =
    Article(
        id = id,
        title = title,
        teaser = teaser,
        content = content,
        isFav = false
    )

fun ArticleEntity.toDomain() =
    Article(
        id = id,
        title = title,
        teaser = teaser,
        content = content,
        isFav = isFav
    )

fun Article.toEntity() =
    ArticleEntity(
        id = id,
        title = title,
        teaser = teaser,
        content = content,
        isFav = isFav
    )