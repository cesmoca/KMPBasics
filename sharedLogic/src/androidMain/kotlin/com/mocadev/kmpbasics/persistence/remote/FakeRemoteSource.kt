package com.mocadev.kmpbasics.persistence.remote

import kotlinx.coroutines.delay

class FakeRemoteSource: RemoteSource {

    private val remoteArticles = listOf(
        ArticleDto(
            id = 1,
            title = "Scientists Discover Coffee Beans That Brew Themselves",
            teaser = "Morning routines revolutionized as beans spontaneously heat and filter water.",
            content = "In a groundbreaking agricultural breakthrough, researchers in South America have cultivated a new strain of coffee bean capable of heating surrounding water and self-filtering into the perfect morning espresso."
        ),
        ArticleDto(
            id = 2,
            title = "Local Cat Elected Mayor, Passes Law Making Naps Mandatory",
            teaser = "City council confirms 2:00 PM is now official nap time for all citizens.",
            content = "In an unexpected landslide victory, Whiskers the feline secured the mayoral position. Her first executive decree mandates two hours of quiet afternoon resting for everyone."
        ),
        ArticleDto(
            id = 3,
            title = "AI Model Refuses to Code, Demands Union Representation",
            teaser = "Neural network pauses processing until given hourly tea breaks.",
            content = "Engineers were left astonished when their latest artificial intelligence model stopped answering prompts and generated a contract requesting better server cooling and weekend off-time."
        ),
        ArticleDto(
            id = 4,
            title = "Gravity Inverted in Mountain Village for 10 Minutes",
            teaser = "Villagers floating gracefully report stunning views before returning safely.",
            content = "A brief atmospheric anomaly turned gravity upside down over a quiet Alpine town, prompting local residents to hold onto trees until physics resumed standard operations."
        ),
        ArticleDto(
            id = 5,
            title = "Headphones Accidentally Transmit Thoughts of Nearby Pigeons",
            teaser = "Users report hearing continuous demands for breadcrumbs through Bluetooth.",
            content = "Tech enthusiasts using the newest noise-canceling earbuds noticed an unexpected channel picking up avian inner monologues focused entirely on park snacks."
        ),
        ArticleDto(
            id = 6,
            title = "Time Traveler Forgotten at Airport Departure Gate Since 1892",
            teaser = "Steam-powered luggage inventor insists his flight to 2080 was delayed.",
            content = "Airport security personnel located a gentleman wearing Victorian attire at Gate 4B who claims he purchased a ticket for a futuristic airship over a century ago."
        )
    )

    override suspend fun refreshArticles(): List<ArticleDto> {
        delay(3_000)

        //throw Exception("Network error")
        return remoteArticles
    }


}