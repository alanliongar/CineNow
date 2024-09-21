package com.devspacecinenow

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieListScreen(navController: NavHostController) {
    var nowPlayingMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
    var upcomingMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
    var topRatedMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }
    var popularMovies by remember { mutableStateOf<List<MovieDto>>(emptyList()) }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    val callNowPlaying = apiService.getNowPlayingMovies()
    callNowPlaying.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    nowPlayingMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }

    })

    val callUpcomingMovies = apiService.getUpcomingMovies()
    callUpcomingMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    upcomingMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    val callTopRatedMovies = apiService.getTopRatedMovies()
    callTopRatedMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    topRatedMovies =
                        movies //isso aqui é uma lista DTO quando a chamada na api é sucesso
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }

    })

    val callPopularMovies = apiService.getPopularMovies()
    callPopularMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    popularMovies =
                        movies //isso aqui é uma lista DTO quando a chamada na api é sucesso
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })
    MovieListContent(
        topRatedMovies,
        upcomingMovies,
        nowPlayingMovies,
        popularMovies
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
    onClick: (MovieDto) -> Unit,
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
            onClick = onClick
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Em breve lançará",
            upcomingMovies,
            onClick = onClick
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Mais aclamados",
            topRatedMovies,
            onClick = onClick
        )
        Spacer(modifier = Modifier.size(15.dp))
        MovieSession(
            "Mais populares",
            popularMovies,
            onClick = onClick
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(fontSize = 24.sp, text = label, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.size(8.dp))
        MovieList(movieList = movieList, onClick = onClick)
    }
}

@Composable
private fun MovieList(movieList: List<MovieDto>, onClick: (MovieDto) -> Unit) {
    LazyRow {
        items(movieList) {
            MovieItem(movieDto = it, onClick = onClick)
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