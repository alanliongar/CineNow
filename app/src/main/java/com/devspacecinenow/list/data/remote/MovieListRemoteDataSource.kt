package com.devspacecinenow.list.data.remote

import android.accounts.NetworkErrorException
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import kotlin.jvm.Throws

class MovieListRemoteDataSource(
    private val listService: ListService
) : RemoteDataSource {
    override suspend fun getNowPlaying(page: Int): Result<List<Movie>?> {
        return try {
            val response = listService.getNowPlayingMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.NowPlaying.name,
                        page = page
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun getUpComing(page: Int): Result<List<Movie>?> {
        return try {
            //throw UnknownHostException()
            val response = listService.getUpcomingMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.UpComing.name,
                        page = page
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun getTopRated(page: Int): Result<List<Movie>?> {
        return try {
            val response = listService.getTopRatedMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.TopRated.name,
                        page = page
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun getPopular(page: Int): Result<List<Movie>?> {
        return try {
            val response = listService.getPopularMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.Popular.name,
                        page = page
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}