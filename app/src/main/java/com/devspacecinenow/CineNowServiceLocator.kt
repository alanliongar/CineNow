package com.devspacecinenow

import android.app.Application
import androidx.room.Room
import com.devspacecinenow.common.data.local.CineNowDataBase
import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.ListService
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource

object CineNowServiceLocator {
/*    fun getRepository(application: Application): MovieListRepository {
        val db = Room.databaseBuilder(
            application.applicationContext,
            CineNowDataBase::class.java,
            "database-cinenow"
        ).build()

        val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)

        val localDataSource: MovieListLocalDataSource = MovieListLocalDataSource(db.getMovieDao())
        val remoteDataSource: MovieListRemoteDataSource = MovieListRemoteDataSource(listService)
        val repository: MovieListRepository =
            MovieListRepository(local = localDataSource, remote = remoteDataSource)
        return repository
    }*/

}
