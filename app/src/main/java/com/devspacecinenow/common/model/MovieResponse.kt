package com.devspacecinenow.common.model

@kotlinx.serialization.Serializable
data class MovieResponse(
    val results: List<MovieDto>
)