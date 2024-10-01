package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow<List<MovieDto>>(emptyList())
    private val _uiUpComingMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    private val _uiTopRatedMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    private val _uiPopularMovies = MutableStateFlow<List<MovieDto>>(emptyList())

    val uiNowPlaying: StateFlow<List<MovieDto>> = _uiNowPlaying
    val uiUpComingMovies: StateFlow<List<MovieDto>> = _uiUpComingMovies
    val TopRatedMovies: StateFlow<List<MovieDto>> = _uiTopRatedMovies
    val PopularMovies: StateFlow<List<MovieDto>> = _uiPopularMovies


    init {
    //chamada dos nowplaying
        fetchNowPlayingMovies()
    //chamada do upcoming
        fetchUpcomingMovies()
    //chamada do topRated
        fetchTopRatedMovies()
    //chamada do popular
        fetchPopularMovies()
    }

    private fun fetchNowPlayingMovies(){
        listService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiNowPlaying.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchUpcomingMovies(){
        listService.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiUpComingMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchTopRatedMovies(){
        listService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiTopRatedMovies.value =
                            movies //isso aqui é uma lista DTO quando a chamada na api é sucesso
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }

        })
    }

    private fun fetchPopularMovies(){
        listService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiPopularMovies.value =
                            movies //isso aqui é uma lista DTO quando a chamada na api é sucesso
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }
        })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(listService) as T
            }
        }
    }
}