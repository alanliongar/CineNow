package com.devspacecinenow.list.data.remote

import com.devspacecinenow.common.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/*&page={page}*/
interface ListService {
    @GET("now_playing?language=pt-BR")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("popular?language=pt-BR")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("top_rated?language=pt-BR")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("upcoming?language=pt-BR")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieResponse>
}