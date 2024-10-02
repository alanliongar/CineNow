package com.devspacecinenow.list.presentation

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.list.data.ListService
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private var currentPageNowPlaying: Int = 1
    private var currentPageUpcoming: Int = 1
    private var currentPageTopRated: Int = 1
    private var currentPagePopular: Int = 1

    private val _uiNowPlaying = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiUpComingMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    private val _uiTopRatedMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    private val _uiPopularMovies = MutableStateFlow<List<MovieDto>>(emptyList())

    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying
    val uiUpComingMovies: StateFlow<List<MovieDto>> = _uiUpComingMovies
    val TopRatedMovies: StateFlow<List<MovieDto>> = _uiTopRatedMovies
    val PopularMovies: StateFlow<List<MovieDto>> = _uiPopularMovies


    init {
        //chamada dos nowplaying
        fetchNowPlayingMovies()
        //chamada do upcoming
        //fetchUpcomingMovies()
        //chamada do topRated
        //fetchTopRatedMovies()
        //chamada do popular
        //fetchPopularMovies()
    }

    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = _uiNowPlaying.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getNowPlayingMovies(currentPageNowPlaying)
                if (response.isSuccessful) {
                    currentPageNowPlaying++
                    val movies = response.body()?.results
                    if (movies != null) {
                        val movieUiDataList = movies.map { movieDto ->
                            MovieUiData(
                                id = movieDto.id,
                                title = movieDto.title,
                                overview = movieDto.overview,
                                image = movieDto.posterFullPath
                            )
                        }
                        _uiNowPlaying.value = _uiNowPlaying.value.copy(
                            list = _uiNowPlaying.value.list + movieUiDataList,
                            isLoading = false, isError = false
                        )
                    }
                } else {
                    _uiNowPlaying.value =
                        _uiNowPlaying.value.copy(isError = true, isLoading = false)
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                _uiNowPlaying.value = _uiNowPlaying.value.copy(isError = true, isLoading = false)
                currentPageNowPlaying = 1
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpcomingMovies(currentPageUpcoming)
            if (response.isSuccessful) {
                currentPageUpcoming++
                val movies = response.body()?.results
                if (movies != null) {
                    _uiUpComingMovies.value = _uiUpComingMovies.value + movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies(currentPageTopRated)
            if (response.isSuccessful) {
                currentPageTopRated++
                val movies = response.body()?.results
                if (movies != null) {
                    _uiTopRatedMovies.value = _uiTopRatedMovies.value + movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies(currentPagePopular)
            if (response.isSuccessful) {
                currentPagePopular++
                val movies = response.body()?.results
                if (movies != null) {
                    _uiPopularMovies.value = _uiPopularMovies.value + movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }


    fun loadMoreTopRated() {
        fetchTopRatedMovies()
    }

    fun loadMoreUpcoming() {
        fetchUpcomingMovies()
    }

    fun loadMoreNowPlaying() {
        fetchNowPlayingMovies()
    }

    fun loadMorePopular() {
        fetchPopularMovies()
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