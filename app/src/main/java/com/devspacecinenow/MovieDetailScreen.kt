package com.devspacecinenow

import android.graphics.Movie
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.ui.theme.CineNowTheme
import org.jetbrains.annotations.Async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

@Composable
fun MovieDetailScreen(movieId: String, navHostController: NavHostController) {
    var movieDto by remember { mutableStateOf<MovieDto?>(null) }
    val apiService: ApiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    apiService.getMovieById(movieId).enqueue(object : Callback<MovieDto> {
        override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
            if (response.isSuccessful) {
                val movie = response.body()
                if (movie != null) {
                    movieDto = movie
                }
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<MovieDto>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })
    movieDto?.let {
        MovieDetailContent(it, navHostController)
    }
}

@Composable
fun MovieDetailContent(movie: MovieDto, navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
            }
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = movie.title,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(195.dp)
                    .width(133.dp),
                contentScale = ContentScale.FillBounds,
                model = movie.posterFullPath,
                contentDescription = "${movie.title} Poster image"
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = movie.overview,
                fontSize = 24.sp,
                //horizontalArrangement = Arrangement.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailPreview() {
    CineNowTheme {
        val movie = MovieDto(
            id = 9,
            title = "Title",
            overview = "Long overview Movie Long overview Movie Long overview Movie Long " +
                    "overview Movie Long overview Movie Long overview Movie Long overview Movie " +
                    "Long overview Movie Long overview Movie Long overview Movie Long overview Movie " +
                    "Long overview Movie Long overview Movie Long overview Movie Long overview Movie Long " +
                    "overview Movie ",
            postPath = "dshaufusahd",
        )
       // MovieDetailContent(movie)
    }
}
