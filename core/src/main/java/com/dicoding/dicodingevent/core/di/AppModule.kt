package com.dicoding.dicodingevent.core.di

import android.content.Context
import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository
import com.dicoding.dicodingevent.core.data.local.LocalDataSource
import com.dicoding.dicodingevent.core.data.remote.RemoteDataSource
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiService
import com.dicoding.dicodingevent.core.domain.usecase.EventInteractor
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
        RemoteDataSource(apiService)

    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource =
        LocalDataSource.getInstance(context)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): FavoriteEventRepository =
        FavoriteEventRepository.getInstance(remoteDataSource, localDataSource)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindEventUseCase(
        eventInteractor: EventInteractor
    ): EventUseCase
}
