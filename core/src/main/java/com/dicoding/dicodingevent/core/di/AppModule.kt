package com.dicoding.dicodingevent.core.di

import com.dicoding.dicodingevent.core.domain.usecase.EventInteractor
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun bindEventUseCase(
        eventInteractor: EventInteractor
    ): EventUseCase
}
