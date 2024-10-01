package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiNowPlaying.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiUpComingMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiTopRatedMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiPopularMovies.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
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