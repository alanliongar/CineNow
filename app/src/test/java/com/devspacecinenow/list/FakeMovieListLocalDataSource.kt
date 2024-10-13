package com.devspacecinenow.list

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.local.LocalDataSource

class FakeMovieListLocalDataSource : LocalDataSource {
    var nowPlaying = emptyList<Movie>()
    var upComing = emptyList<Movie>()
    var topRated = emptyList<Movie>()
    var popular = emptyList<Movie>()
    var updateItems = emptyList<Movie>()
    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        return nowPlaying
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
        return upComing
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return topRated
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return popular
    }

    override suspend fun updateLocalItems(movies: List<Movie>) {
        updateItems = movies
    }
}