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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieListRepository
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
            val result = repository.getNowPlaying(currentPageNowPlaying)
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
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiNowPlaying.value = _uiNowPlaying.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                }else{
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
        _uiUpComingMovies.value = _uiUpComingMovies.value.copy(isLoading = true, isError = false)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUpcoming(currentPageUpcoming)
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
                    _uiUpComingMovies.value = _uiUpComingMovies.value.copy(
                        list = _uiUpComingMovies.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiUpComingMovies.value = _uiUpComingMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                }else{
                    _uiUpComingMovies.value = _uiUpComingMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPageUpcoming = 1
                Log.d("MovieListViewModel", "Request Error :: ${result.toString()}")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(isLoading = true, isError = false)
        viewModelScope.launch(Dispatchers.IO) {
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
                    _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(
                        list = _uiTopRatedMovies.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                }else{
                    _uiTopRatedMovies.value = _uiTopRatedMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPageTopRated = 1
                Log.d("MovieListViewModel", "Request Error :: ${result.toString()}")
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiPopularMovies.value = _uiPopularMovies.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
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
                    _uiPopularMovies.value = _uiPopularMovies.value.copy(
                        list = _uiPopularMovies.value.list + movieUiDataList,
                        isLoading = false,
                        isError = false
                    )
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiPopularMovies.value = _uiPopularMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Sem internet",
                        list = emptyList()
                    )
                }else{
                    _uiPopularMovies.value = _uiPopularMovies.value.copy(
                        isError = true,
                        isLoading = false,
                        list = emptyList()
                    )
                }
                currentPagePopular = 1
                Log.d("MovieListViewModel", "Request Error :: ${result.toString()}")
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

                return MovieListViewModel(repository =(application as CineNowApplication).repository) as T
            }
        }
    }
}