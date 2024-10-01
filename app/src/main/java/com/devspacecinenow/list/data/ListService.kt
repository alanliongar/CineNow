package com.devspacecinenow.list.data

import com.devspacecinenow.common.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ListService {
    @GET("now_playing?language=pt-BR&page=1")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("popular?language=pt-BR&page=1")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("top_rated?language=pt-BR&page=1")
    fun getTopRatedMovies(): Call<MovieResponse>

    @GET("upcoming?language=pt-BR&page=1")
    fun getUpcomingMovies(): Call<MovieResponse>
}