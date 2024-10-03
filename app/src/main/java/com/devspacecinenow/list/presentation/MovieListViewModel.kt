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
import java.net.UnknownHostException

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private var currentPageNowPlaying: Int = 1
    private var currentPageUpcoming: Int = 1
    private var currentPageTopRated: Int = 1
    private var currentPagePopular: Int = 1

    private val _uiNowPlaying = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiUpComingMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiTopRatedMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiPopularMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())

    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying
    val uiUpComingMovies: StateFlow<MovieListUiState> = _uiUpComingMovies
    val TopRatedMovies: StateFlow<MovieListUiState> = _uiTopRatedMovies
    val PopularMovies: StateFlow<MovieListUiState> = _uiPopularMovies


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
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value =
                        _uiNowPlaying.value.copy(
                            isError = true,
                            errorMessage = "Sem internet",
                            isLoading = false
                        )
                } else {
                    _uiNowPlaying.value =
                        _uiNowPlaying.value.copy(isError = true, isLoading = false)
                }
                currentPageNowPlaying = 1
            }
        }
    }

    private fun fetchUpcomingMovies() {
        _uiUpComingMovies.value = _uiUpComingMovies.value.copy(isLoading = true, isError = false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getUpcomingMovies(currentPageUpcoming)
                if (response.isSuccessful) {
                    currentPageUpcoming++
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
                        _uiUpComingMovies.value = _uiUpComingMovies.value.copy(
                            list = _uiUpComingMovies.value.list + movieUiDataList,
                            isLoading = false, isError = false
                        )
                    }
                } else {
                    _uiUpComingMovies.value =
                        _uiUpComingMovies.value.copy(isError = true, isLoading = false)
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException) {
                    _uiUpComingMovies.value = _uiUpComingMovies.value.copy(
                        isError = true,
                        errorMessage = "Sem internet",
                        isLoading = false
                    )
                } else {
                    _uiUpComingMovies.value =
                        _uiUpComingMovies.value.copy(isError = true, isLoading = false)
                }
                currentPageUpcoming = 1
                Log.d("MovieListViewModel", "Erro na requisição")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getTopRatedMovies(currentPageTopRated)
                if (response.isSuccessful) {
                    currentPageTopRated++
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
                        _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(
                            list = _uiTopRatedMovies.value.list + movieUiDataList,
                            isLoading = false, isError = false
                        )
                    }
                } else {
                    _uiTopRatedMovies.value =
                        _uiTopRatedMovies.value.copy(isError = true, isLoading = false)
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException) {
                    _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(
                        isError = true,
                        errorMessage = "Sem internet",
                        isLoading = false
                    )
                } else {
                    _uiTopRatedMovies.value =
                        _uiTopRatedMovies.value.copy(isError = true, isLoading = false)
                }
                currentPageTopRated = 1
                Log.d("MovieListViewModel", "Erro na requisição")
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiPopularMovies.value = _uiPopularMovies.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getPopularMovies(currentPagePopular)
                if (response.isSuccessful) {
                    currentPagePopular++
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
                        _uiPopularMovies.value = _uiPopularMovies.value.copy(
                            list = _uiPopularMovies.value.list + movieUiDataList,
                            isLoading = false, isError = false
                        )
                    }
                } else {
                    _uiPopularMovies.value =
                        _uiPopularMovies.value.copy(isError = true, isLoading = false)
                    Log.d("MovieListViewModel", "Request Error :: ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException) {
                    _uiPopularMovies.value = _uiPopularMovies.value.copy(
                        isError = true,
                        errorMessage = "Sem internet",
                        isLoading = false
                    )
                } else {
                    _uiPopularMovies.value =
                        _uiPopularMovies.value.copy(isError = true, isLoading = false)
                }
                currentPagePopular = 1
                Log.d("MovieListViewModel", "Erro na requisição")
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