package com.devspacecinenow.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.list.presentation.MovieListViewModel

@Composable
fun MovieListScreen(navController: NavHostController, viewModel: MovieListViewModel) {

    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()
    val upcomingMovies by viewModel.uiUpComing.collectAsState()
    val topRatedMovies by viewModel.uiTopRated.collectAsState()
    val popularMovies by viewModel.uiPopular.collectAsState()

    MovieListContent(
        topRatedMovies,
        upcomingMovies,
        nowPlayingMovies,
        popularMovies,
        viewModel::loadMoreTopRated,
        viewModel::loadMoreUpcoming,
        viewModel::loadMoreNowPlaying,
        viewModel::loadMorePopular
    ) { movieClicked ->
        navController.navigate(route = "movieDetail/${movieClicked.id/*.toString()*/}")
    }
}

@Composable
fun MovieListContent(
    topRatedMovies: MovieListUiState,
    upcomingMovies: MovieListUiState,
    nowPlayingMovies: MovieListUiState,
    popularMovies: MovieListUiState,
    loadMoreTopRated: () -> Unit,
    loadMoreUpcoming: () -> Unit,
    loadMoreNowPlaying: () -> Unit,
    loadMorePopular: () -> Unit,
    onClick: (MovieUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "CineNow"
        )
        MovieSession(
            "Em Cartaz",
            nowPlayingMovies,
            onClick = onClick,
            loadMore = loadMoreNowPlaying
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Em breve lançará",
            upcomingMovies,
            onClick = onClick,
            loadMore = loadMoreUpcoming
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Mais aclamados",
            topRatedMovies,
            onClick = onClick,
            loadMore = loadMoreTopRated
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Mais populares",
            popularMovies,
            onClick = onClick,
            loadMore = loadMorePopular
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MovieListPreview(modifier: Modifier = Modifier) {
}*/

@Composable
private fun MovieSession(
    label: String,
    movieListUiState: MovieListUiState,
    onClick: (MovieUiData) -> Unit,
    modifier: Modifier = Modifier,
    loadMore: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(fontSize = 24.sp, text = label, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.size(8.dp))
        if (movieListUiState.isLoading) {
            Text(color = Color.Green, text = "CARREGANDO", fontWeight = FontWeight.SemiBold)
        } else if (movieListUiState.isError) {
            Text(
                color = Color.Red,
                text = movieListUiState.errorMessage ?: "",
                fontWeight = FontWeight.SemiBold
            )
        } else {
            MovieList(movieList = movieListUiState.list, onClick = onClick, loadMore = loadMore)
        }
    }
}

@Composable
private fun MovieList(
    movieList: List<MovieUiData>, onClick: (MovieUiData) -> Unit,
    loadMore: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(movieList) {
            MovieItem(movieUiData = it, onClick = onClick)
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = loadMore) {
                    Text("Carregar")
                }
            }
        }
    }
}

@Composable
private fun MovieItem(movieUiData: MovieUiData, onClick: (MovieUiData) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick.invoke(movieUiData) }
            .width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .width((0 + 120).dp)
                .height((0 + 180).dp),
            contentScale = ContentScale.FillBounds,
            model = movieUiData.image, contentDescription = "${movieUiData.title} Poster image"
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = movieUiData.title,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = movieUiData.overview, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}