package com.devspacecinenow.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.list.presentation.MovieListViewModel

@Composable
fun MovieListScreen(navController: NavHostController, viewModel: MovieListViewModel) {

    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()
    val upcomingMovies by viewModel.uiUpComingMovies.collectAsState()
    val topRatedMovies by viewModel.TopRatedMovies.collectAsState()
    val popularMovies by viewModel.PopularMovies.collectAsState()

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
    topRatedMovies: List<MovieDto>,
    upcomingMovies: List<MovieDto>,
    nowPlayingMovies: List<MovieDto>,
    popularMovies: List<MovieDto>,
    loadMoreTopRated: () -> Unit,
    loadMoreUpcoming: () -> Unit,
    loadMoreNowPlaying: () -> Unit,
    loadMorePopular: () -> Unit,
    onClick: (MovieDto) -> Unit
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
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier,
    loadMore: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(fontSize = 24.sp, text = label, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.size(8.dp))
        MovieList(movieList = movieList, onClick = onClick, loadMore = loadMore)
    }
}

@Composable
private fun MovieList(
    movieList: List<MovieDto>, onClick: (MovieDto) -> Unit,
    loadMore: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(movieList) {
            MovieItem(movieDto = it, onClick = onClick)
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
private fun MovieItem(movieDto: MovieDto, onClick: (MovieDto) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick.invoke(movieDto) }
            .width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .width((0 + 120).dp)
                .height((0 + 180).dp),
            contentScale = ContentScale.FillBounds,
            model = movieDto.posterFullPath, contentDescription = "${movieDto.title} Poster image"
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = movieDto.title,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = movieDto.overview, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}