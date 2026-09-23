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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocadev.kmpbasics.domain.Article
import com.mocadev.kmpbasics.viewmodels.ArticleListUiState
import com.mocadev.kmpbasics.viewmodels.ArticleListViewModel

@Composable
@Preview
fun App(viewModel: ArticleListViewModel = viewModel { ArticleListViewModel() }) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppContent(
                uiState = uiState,
                onUiEvent = { event -> viewModel.onUiEvent(event) }
            )
        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    uiState: ArticleListUiState,
    onUiEvent: (ArticleListViewModel.ArticleListUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(top = 25.dp))
    ) {
        ArticleList(
            uiState = uiState,
            onUiEvent = { event -> onUiEvent(event) },
            modifier = Modifier
                .weight(1f)
        )

        BottomFilterBox(
            uiState = uiState,
            onUiEvent = { event -> onUiEvent(event) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    uiState: ArticleListUiState,
    onUiEvent: (ArticleListViewModel.ArticleListUiEvent) -> Unit
) {

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { onUiEvent(ArticleListViewModel.ArticleListUiEvent.RefreshArticles) },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = uiState.articlesList,
                key = { article -> article.id }
            ) { article ->
                ArticleItem(
                    article = article,
                    onClick = { id -> onUiEvent(ArticleListViewModel.ArticleListUiEvent.ToggleFavArticle(id)) }
                )
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(article.id) },
        colors = CardDefaults.cardColors(
            containerColor = if (article.isFav) Color(0xFFFFF9C4) else CardDefaults.cardColors().containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
}

@Composable
fun BottomFilterBox(
    modifier: Modifier = Modifier,
    uiState: ArticleListUiState,
    onUiEvent: (ArticleListViewModel.ArticleListUiEvent) -> Unit
) {

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
                    checked = uiState.onlyFavs,
                    onCheckedChange = { onUiEvent(ArticleListViewModel.ArticleListUiEvent.ToggleFilterOnlyFav) }
                )
            }

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    onUiEvent(
                        ArticleListViewModel.ArticleListUiEvent.UpdateSearchQuery(
                            it
                        )
                    )
                },
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
fun AppContentPreview() {

    val articles = listOf(
        Article(1, "Article 1", "Teaser 1", content = "Content 1", isFav = true),
        Article(2, "Article 2", "Teaser 2", content = "Content 2", isFav = false),
        Article(3, "Article 3", "Teaser 3", content = "Content 3", isFav = false)
    )

    val searchQuery = "united states"
    val onlyFavs = true

    val uiState = ArticleListUiState(
        articles,
        searchQuery,
        onlyFavs
    )

    AppContent(
        uiState = uiState,
        onUiEvent = {}
    )
}