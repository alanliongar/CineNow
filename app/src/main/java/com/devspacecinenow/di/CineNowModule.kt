package com.devspacecinenow.di

import android.app.Application
import androidx.room.Room
import com.devspacecinenow.common.data.local.CineNowDataBase
import com.devspacecinenow.common.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.retrofitInstance
    }
}