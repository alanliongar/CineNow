package com.devspacecinenow.di

import android.app.Application
import androidx.room.Room
import com.devspacecinenow.common.data.local.CineNowDataBase
import com.devspacecinenow.common.data.local.MovieDao
import com.devspacecinenow.common.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CineNowModule {
    @Provides
    fun provideCineNowDataBase(application: Application): CineNowDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            CineNowDataBase::class.java,
            "database-cinenow"
        ).build()
    }

    @Provides
    fun provideMovieDao(roomDatabase: CineNowDataBase): MovieDao {
        return roomDatabase.getMovieDao()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.retrofitInstance
    }

    @Provides
    @DispatcherIO
    fun providesDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun providesLongType(): Long {
        return 0L
    }
}