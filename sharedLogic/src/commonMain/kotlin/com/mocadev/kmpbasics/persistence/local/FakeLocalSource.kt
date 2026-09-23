package com.mocadev.kmpbasics.persistence.local

import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.persistence.remote.ArticleDto
import com.mocadev.kmpbasics.persistence.toDomain
import com.mocadev.kmpbasics.persistence.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeLocalSource : LocalSource {

    private val dbArticles = MutableStateFlow(
        listOf(
            ArticleEntity(
                id = 1,
                title = "Scientists Discover Coffee Beans That Brew Themselves",
                teaser = "Morning routines revolutionized as beans spontaneously heat and filter water.",
                content = "In a groundbreaking agricultural breakthrough, researchers in South America have cultivated a new strain of coffee bean capable of heating surrounding water and self-filtering into the perfect morning espresso.",
                isFav = false

            ),
            ArticleEntity(
                id = 2,
                title = "Local Cat Elected Mayor, Passes Law Making Naps Mandatory",
                teaser = "City council confirms 2:00 PM is now official nap time for all citizens.",
                content = "In an unexpected landslide victory, Whiskers the feline secured the mayoral position. Her first executive decree mandates two hours of quiet afternoon resting for everyone.",
                isFav = false

            ),
            ArticleEntity(
                id = 3,
                title = "AI Model Refuses to Code, Demands Union Representation",
                teaser = "Neural network pauses processing until given hourly tea breaks.",
                content = "Engineers were left astonished when their latest artificial intelligence model stopped answering prompts and generated a contract requesting better server cooling and weekend off-time.",
                isFav = false
            )
        )
    )

    override fun observeArticles(): Flow<List<ArticleEntity>> =  dbArticles

    override fun updateArticles(remoteArticles: List<ArticleEntity>) {
        // We need to preserve the users favorites list
        val favArticlesEntity = dbArticles.value.filter { it.isFav }
        val remoteArticlesEntity = remoteArticles.map { it.toDomain().toEntity() }

        // Now let's preserve the favs in the new articles
        dbArticles.value =
            remoteArticlesEntity.map { remoteArticle ->
                val isFav = favArticlesEntity.any({ it.id == remoteArticle.id })

                ArticleEntity(
                    id = remoteArticle.id,
                    title = remoteArticle.title,
                    teaser = remoteArticle.teaser,
                    content = remoteArticle.content,
                    isFav = isFav
                )

            }
    }

    override fun toggleFavArticle(id: Int) {
        dbArticles.value = dbArticles.value.map { article ->
            if (article.id == id) {
                ArticleEntity(
                    id = article.id,
                    title = article.title,
                    teaser = article.teaser,
                    content = article.content,
                    isFav = !article.isFav
                )
            } else article
        }
    }
}