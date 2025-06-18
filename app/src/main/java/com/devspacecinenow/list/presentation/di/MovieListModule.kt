package com.devspacecinenow.list.presentation.di

import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.list.data.local.LocalDataSource
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.ListService
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import com.devspacecinenow.list.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MovieListModule {
    @Provides
    fun provideListService(retrofit: Retrofit): ListService {
        return retrofit.create(ListService::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface MovieListModuleBinding {

    @Binds
    fun bindLocalDataSource(impl: MovieListLocalDataSource): LocalDataSource

    @Binds
    fun bindRemoteDataSource(impl: MovieListRemoteDataSource): RemoteDataSource
}