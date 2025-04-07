package com.dicoding.dicodingevent.core.di

import android.content.Context
import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository
import com.dicoding.dicodingevent.core.data.local.LocalDataSource
import com.dicoding.dicodingevent.core.data.remote.RemoteDataSource
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingevent.core.domain.usecase.EventInteractor
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase

object Injection {
    fun provideRepository(context: Context): FavoriteEventRepository {
        val remoteDataSource = RemoteDataSource(ApiConfig.getApiService())
        val localDataSource = LocalDataSource.getInstance(context)
        return FavoriteEventRepository.getInstance(remoteDataSource, localDataSource)
    }

    fun provideEventUseCase(context: Context): EventUseCase {
        val repository = provideRepository(context)
        return EventInteractor(repository)
    }
}