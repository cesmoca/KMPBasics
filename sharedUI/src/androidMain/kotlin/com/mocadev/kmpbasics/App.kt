package com.mocadev.kmpbasics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.viewmodels.ArticleListViewModel

@Composable
@Preview
fun App(viewModel: ArticleListViewModel = viewModel { ArticleListViewModel() }) {
    val articles by viewModel.articlesList.collectAsState(emptyList())

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ArticleList(
                    articles = articles,
                    modifier = Modifier.weight(1f)
                )

                BottomFilterBox()
            }
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = articles,
            key = { article -> article.id }
        ) { article ->
            ArticleItem(article = article)
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = article.teaser,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun BottomFilterBox(
    modifier: Modifier = Modifier
) {
    var onlyFavs by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 3.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Only favs",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = onlyFavs,
                    onCheckedChange = { onlyFavs = it }
                )
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = { Text("Search...") },
                leadingIcon = { Text("🔍", modifier = Modifier.padding(start = 8.dp)) },
                singleLine = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleListPreview() {
    val articles = listOf(
        Article(1, "Article 1", "Teaser 1", content = "Content 1"),
        Article(2, "Article 2", "Teaser 2", content = "Content 2"),
        Article(3, "Article 3", "Teaser 3", content = "Content 3")
    )
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ArticleList(articles, modifier = Modifier.weight(1f))
            BottomFilterBox()
        }
    }
}