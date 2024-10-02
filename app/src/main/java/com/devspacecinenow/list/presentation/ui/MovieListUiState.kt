package com.devspacecinenow.list.presentation.ui

data class MovieListUiState(
    val list: List<MovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class MovieUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
)