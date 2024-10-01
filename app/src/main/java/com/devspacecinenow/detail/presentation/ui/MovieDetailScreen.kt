package com.devspacecinenow.detail.presentation.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import com.devspacecinenow.ui.theme.CineNowTheme


@Composable
fun MovieDetailScreen(movieId: String, navHostController: NavHostController, detailViewModel: MovieDetailViewModel) {
    val movieDto by detailViewModel.uiMovieDetail.collectAsState()
    detailViewModel.fetchMovieDetail(movieId)

    movieDto?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    detailViewModel.cleanMovieID()
                    navHostController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title,
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
                    model = it.posterFullPath,
                    contentDescription = "${it.title} Poster image"
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = it.overview,
                    fontSize = 24.sp,
                    //horizontalArrangement = Arrangement.Center
                )
            }
        }
    }
}

/*@Composable
fun MovieDetailContent(movie: MovieDto, navHostController: NavHostController) {

}*/

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
