package com.devspacecinenow.common.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val results: List<MovieDto>
)