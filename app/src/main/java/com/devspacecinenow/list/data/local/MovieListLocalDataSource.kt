package com.devspacecinenow.list.data.local

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.local.MovieDao
import com.devspacecinenow.common.data.local.MovieEntity
import com.devspacecinenow.common.data.model.Movie

class MovieListLocalDataSource(private val dao: MovieDao): LocalDataSource {


    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        return getMoviesByCategory(MovieCategory.NowPlaying, page)
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return getMoviesByCategory(MovieCategory.TopRated, page)
    }

    override suspend fun getUpComingMovies(page: Int): List<Movie> {
        return getMoviesByCategory(MovieCategory.UpComing, page)
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return getMoviesByCategory(MovieCategory.Popular, page)
    }

    override suspend fun updateLocalItems(movies: List<Movie>) {
        dao.insertAll(
            movies.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    image = it.image,
                    category = it.category,
                    page = it.page
                )
            }
        )
    }

    private suspend fun getMoviesByCategory(movieCategory: MovieCategory, page: Int): List<Movie> {
        val entities = dao.getMoviesByCategory(movieCategory.name, page)
        return entities.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category,
                page = it.page
            )
        }
    }
}