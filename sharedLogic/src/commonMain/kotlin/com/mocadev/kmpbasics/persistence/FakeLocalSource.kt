package com.mocadev.kmpbasics.persistence

import com.mocadev.kmpbasics.domain.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalSource: LocalSource {

    private val sampleArticles = listOf(
        Article(
            id = 1,
            title = "Scientists Discover Coffee Beans That Brew Themselves",
            teaser = "Morning routines revolutionized as beans spontaneously heat and filter water.",
            content = "In a groundbreaking agricultural breakthrough, researchers in South America have cultivated a new strain of coffee bean capable of heating surrounding water and self-filtering into the perfect morning espresso."
        ),
        Article(
            id = 2,
            title = "Local Cat Elected Mayor, Passes Law Making Naps Mandatory",
            teaser = "City council confirms 2:00 PM is now official nap time for all citizens.",
            content = "In an unexpected landslide victory, Whiskers the feline secured the mayoral position. Her first executive decree mandates two hours of quiet afternoon resting for everyone."
        ),
        Article(
            id = 3,
            title = "AI Model Refuses to Code, Demands Union Representation",
            teaser = "Neural network pauses processing until given hourly tea breaks.",
            content = "Engineers were left astonished when their latest artificial intelligence model stopped answering prompts and generated a contract requesting better server cooling and weekend off-time."
        )
    )

    override fun observeArticles(): Flow<List<Article>> = flow {
        emit(sampleArticles)
    }
}