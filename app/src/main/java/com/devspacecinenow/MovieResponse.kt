package com.devspacecinenow

@kotlinx.serialization.Serializable
data class MovieResponse(
    val results: List<MovieDto>

)