package com.devspacecinenow

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("now_playing?language=pt-BR&page=1")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("popular?language=pt-BR&page=1")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("top_rated?language=pt-BR&page=1")
    fun getTopRatedMovies(): Call<MovieResponse>

    @GET("upcoming?language=pt-BR&page=1")
    fun getUpcomingMovies(): Call<MovieResponse>

    @GET("{movie_id}?language=pt-BR")
    fun getMovieById(@Path("movie_id") movieId: String): Call<MovieDto>
}