package com.dicoding.dicodingevent.core.di

import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository
import com.dicoding.dicodingevent.core.domain.repository.IEventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEventRepository(
        favoriteEventRepository: FavoriteEventRepository
    ): IEventRepository
}
