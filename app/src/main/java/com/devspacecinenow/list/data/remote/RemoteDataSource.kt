package com.devspacecinenow.list.data.remote

import com.devspacecinenow.common.data.model.Movie
import kotlin.jvm.Throws

interface RemoteDataSource {
    @Throws(Exception::class)
    suspend fun getNowPlaying(page: Int): Result<List<Movie>?>

    @Throws(Exception::class)
    suspend fun getUpComing(page: Int): Result<List<Movie>?>

    @Throws(Exception::class)
    suspend fun getTopRated(page: Int): Result<List<Movie>?>

    @Throws(Exception::class)
    suspend fun getPopular(page: Int): Result<List<Movie>?>
}