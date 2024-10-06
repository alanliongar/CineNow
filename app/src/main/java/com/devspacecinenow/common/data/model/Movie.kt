package com.devspacecinenow.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
    val category: String,
    val page: Int,
)