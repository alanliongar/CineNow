package com.devspacecinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val detailService: DetailService,
) : ViewModel() {
    private val _uiMovieDetail = MutableStateFlow<MovieDto?>(null)
    val uiMovieDetail: StateFlow<MovieDto?> = _uiMovieDetail

    fun fetchMovieDetail(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (_uiMovieDetail.value == null) {
                    val response = detailService.getMovieById(movieId)
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie != null) {
                            _uiMovieDetail.value = movie
                        }
                    } else {
                        Log.d("MovieDetailViewModel", "Request Error :: ${response.errorBody()}")
                    }

                } else {
                    Log.d("MovieDetailViewModel", "Ja tem um valor lá dentro")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun cleanMovieID() {
        viewModelScope.launch {
            delay(1000)
            _uiMovieDetail.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(detailService) as T
            }
        }
    }
}