package com.devspacecinenow.list.data.remote

import com.devspacecinenow.common.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListService {
    @GET("now_playing?language=en-US")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("popular?language=en-US")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("top_rated?language=en-US")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("upcoming?language=en-US")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieResponse>
}