package com.mocadev.kmpbasics.persistence.local

import com.mocadev.kmpbasics.persistence.toDomain
import com.mocadev.kmpbasics.persistence.toEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeLocalSource : LocalSource {

    private val dbArticles =
        mutableListOf(
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

    private val _trigger = MutableStateFlow(false)

    private val observedArticles = MutableStateFlow<List<ArticleEntity>>(dbArticles)

    override fun observeArticles(): Flow<List<ArticleEntity>> {
        return _trigger.map { observedArticles.value }
    }

    override suspend fun updateArticles(remoteArticles: List<ArticleEntity>) {
        // We need to preserve the users favorites list
        val favArticlesEntity = dbArticles.filter { it.isFav }.toList()
        val remoteArticlesEntity = remoteArticles.map { it.toDomain().toEntity() }

        dbArticles.clear()

        dbArticles.addAll(remoteArticlesEntity.map { remoteArticle ->
            val isFav = favArticlesEntity.any({ it.id == remoteArticle.id })

            ArticleEntity(
                id = remoteArticle.id,
                title = remoteArticle.title,
                teaser = remoteArticle.teaser,
                content = remoteArticle.content,
                isFav = isFav
            )
        })

        _trigger.value = !_trigger.value
    }

    override suspend fun toggleFavArticle(id: Int): Boolean {
        var newIsFav = false

        dbArticles.forEach { article ->
            if (article.id == id) {
                article.isFav = !article.isFav
                newIsFav = article.isFav
                return@forEach
            }
        }

        _trigger.value = !_trigger.value

        return newIsFav
    }

    override suspend fun searchArticles(query: String) {
        delay(250)
        observedArticles.value =
            if (query.isEmpty()) dbArticles
            else dbArticles.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.teaser.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true)
            }

        _trigger.value = !_trigger.value
    }
}