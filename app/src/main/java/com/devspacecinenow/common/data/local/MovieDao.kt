package com.devspacecinenow.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {

    @Query("Select * From movieentity where category = :category and page=:page")
    suspend fun getMoviesByCategory(category: String, page: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)//cuidado
    suspend fun insertAll(movies: List<MovieEntity>)
}