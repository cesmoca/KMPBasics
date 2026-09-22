package com.mocadev.kmpbasics


data class Article(
    val id: Int,
    val title: String,
)
class Articles(private val platformInfo: IPlatform) {

    fun getArticle(): Article{
        return Article(0, "Hello from my friend ${platformInfo.name()}")
    }
}