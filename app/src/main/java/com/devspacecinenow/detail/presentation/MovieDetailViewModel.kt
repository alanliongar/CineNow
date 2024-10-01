package com.devspacecinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel(
    private val detailService: DetailService,
) : ViewModel() {
    private val _uiMovieDetail = MutableStateFlow<MovieDto?>(null)
    val uiMovieDetail: StateFlow<MovieDto?> = _uiMovieDetail

    fun fetchMovieDetail(movieId: String) {
        if (_uiMovieDetail.value == null) {
            detailService.getMovieById(movieId).enqueue(object : Callback<MovieDto> {
                override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie != null) {
                            _uiMovieDetail.value = movie
                        }
                    } else {
                        Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                    Log.d("MainActivity", "Network Error :: ${t.message}")
                }
            })
        } else {

        }
    }

    fun cleanMovieID() {
        viewModelScope.launch{
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