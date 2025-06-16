package com.devspacecinenow.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([MovieEntity::class], version = 5)
abstract class CineNowDataBase: RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}