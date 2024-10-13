package com.devspacecinenow.list.data.local

import com.devspacecinenow.common.data.model.Movie

interface LocalDataSource {
    suspend fun getNowPlayingMovies(page: Int): List<Movie>
    suspend fun getUpcomingMovies(page: Int): List<Movie>
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun updateLocalItems(movies: List<Movie>)
}