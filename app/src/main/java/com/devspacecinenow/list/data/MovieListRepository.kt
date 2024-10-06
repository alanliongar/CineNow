package com.devspacecinenow.list.data

import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import java.io.IOException
import java.net.SocketTimeoutException

class MovieListRepository(
    private val local: MovieListLocalDataSource,
    private val remote: MovieListRemoteDataSource,
) {
    suspend fun getNowPlaying(page: Int): Result<List<Movie>?> {
        return try {
            val result = remote.getNowPlaying(page)
            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                Result.success(local.getNowPlayingMovies(page))
            } else {
                // If remote call fails, return local data
                Result.success(local.getNowPlayingMovies(page))
            }
        } catch (ex: Exception) {
            // If exception occurs, return local data
            if (ex is IOException || ex is SocketTimeoutException) {
                Result.success(local.getNowPlayingMovies(page))
            } else {
                ex.printStackTrace()
                Result.failure(ex)
            }
        }
    }

    suspend fun getUpcoming(page: Int): Result<List<Movie>?> {
        return try {
            val result = remote.getUpcoming(page)
            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                Result.success(local.getUpComingMovies(page))
            } else {
                // If remote call fails, return local data
                Result.success(local.getUpComingMovies(page))
            }
        } catch (ex: Exception) {
            // If exception occurs, return local data
            if (ex is IOException || ex is SocketTimeoutException) {
                Result.success(local.getUpComingMovies(page))
            } else {
                ex.printStackTrace()
                Result.failure(ex)
            }
        }
    }

    suspend fun getTopRated(page: Int): Result<List<Movie>?> {
        return try {
            val result = remote.getTopRated(page)
            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                Result.success(local.getTopRatedMovies(page))
            } else {
                // If remote call fails, return local data
                Result.success(local.getTopRatedMovies(page))
            }
        } catch (ex: Exception) {
            // If exception occurs, return local data
            if (ex is IOException || ex is SocketTimeoutException) {
                Result.success(local.getTopRatedMovies(page))
            } else {
                ex.printStackTrace()
                Result.failure(ex)
            }
        }
    }

    suspend fun getPopular(page: Int): Result<List<Movie>?> {
        return try {
            val result = remote.getPopular(page)
            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                Result.success(local.getPopularMovies(page))
            } else {
                // If remote call fails, return local data
                Result.success(local.getPopularMovies(page))
            }
        } catch (ex: Exception) {
            // If exception occurs, return local data
            if (ex is IOException || ex is SocketTimeoutException) {
                Result.success(local.getPopularMovies(page))
            } else {
                ex.printStackTrace()
                Result.failure(ex)
            }
        }
    }
}