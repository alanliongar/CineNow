package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.devspacecinenow.CineNowApplication
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
//kk
class MovieListViewModel(
    private val repository: MovieListRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var currentPageNowPlaying: Int = 1
    private var currentPageUpcoming: Int = 1
    private var currentPageTopRated: Int = 1
    private var currentPagePopular: Int = 1

    private val _uiNowPlaying = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiUpComing = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiTopRated = MutableStateFlow<MovieListUiState>(MovieListUiState())
    private val _uiPopular = MutableStateFlow<MovieListUiState>(MovieListUiState())

    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying
    val uiUpComing: StateFlow<MovieListUiState> = _uiUpComing
    val uiTopRated: StateFlow<MovieListUiState> = _uiTopRated
    val uiPopular: StateFlow<MovieListUiState> = _uiPopular


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
        viewModelScope.launch(dispatcher) {
            val result = repository.getNowPlaying(currentPageNowPlaying)
            println("MovieListViewModel Result: ${result}")
            if (result.isSuccess) {
                currentPageNowPlaying++
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUiDataList = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.image
                        )
                    }
                    _uiNowPlaying.value = _uiNowPlaying.value.copy(
                        list = _uiNowPlaying.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                    println("MovieListViewModel, Movies fetched: $movieUiDataList")
                } else {
                    _uiNowPlaying.value = _uiNowPlaying.value.copy(
                        list = _uiNowPlaying.value.list + emptyList(),
                        isLoading = false,
                        isError = false
                    )
                }
                //se for um resultado vazio, simplesmente retorne uma lista vazia, não faça nada!
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value = _uiNowPlaying.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                } else {
                    _uiNowPlaying.value = _uiNowPlaying.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPageNowPlaying = 1
                Log.d("MovieListViewModel", "Request Error :: ${result.toString()}")
            }
        }
    }

    private fun fetchUpcomingMovies() {
        _uiUpComing.value = _uiUpComing.value.copy(isLoading = true, isError = false)
        viewModelScope.launch(dispatcher) {
            val result = repository.getUpComing(currentPageUpcoming)
            if (result.isSuccess) {
                currentPageUpcoming++
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUiDataList = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.image
                        )
                    }
                    _uiUpComing.value = _uiUpComing.value.copy(
                        list = _uiUpComing.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                } else {
                    _uiUpComing.value = _uiUpComing.value.copy(
                        list = _uiUpComing.value.list + emptyList(),
                        isLoading = false,
                        isError = false
                    )
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiUpComing.value = _uiUpComing.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                } else {
                    _uiUpComing.value = _uiUpComing.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPageUpcoming = 1
                Log.d("MovieListViewModel", "Request Error :: ${result}")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        //GWT

        //Given create new instance of view model

        //when collect uiTopRated state flow

        //then verify if data is as expected

        _uiTopRated.value = _uiTopRated.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            val result = repository.getTopRated(currentPageTopRated)
            if (result.isSuccess) {
                currentPageTopRated++
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUiDataList = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.image
                        )
                    }
                    _uiTopRated.value = _uiTopRated.value.copy(
                        list = _uiTopRated.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                } else {
                    _uiTopRated.value = _uiTopRated.value.copy(
                        list = _uiTopRated.value.list + emptyList(),
                        isLoading = false,
                        isError = false
                    )
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiTopRated.value = _uiTopRated.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                } else {
                    _uiTopRated.value = _uiTopRated.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPageTopRated = 1
                Log.d("MovieListViewModel", "Request Error :: ${result}")
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiPopular.value = _uiPopular.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            val result = repository.getPopular(currentPagePopular)
            if (result.isSuccess) {
                currentPagePopular++
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUiDataList = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.image
                        )
                    }
                    _uiPopular.value = _uiPopular.value.copy(
                        list = _uiPopular.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                } else {

                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiPopular.value = _uiPopular.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                } else {
                    _uiPopular.value = _uiPopular.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPagePopular = 1
                Log.d("MovieListViewModel", "Request Error :: ${result}")
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
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return MovieListViewModel(repository = (application as CineNowApplication).repository) as T
            }
        }
    }
}