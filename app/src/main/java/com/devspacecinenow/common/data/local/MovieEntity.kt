package com.devspacecinenow.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
    val category: String,
    val page: Int
){

}
