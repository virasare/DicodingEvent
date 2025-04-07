package com.dicoding.dicodingevent.core.di


import android.content.Context
import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository
import com.dicoding.dicodingevent.core.domain.usecase.EventInteractor
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase

object Injection {
    fun provideRepository(application: Context): FavoriteEventRepository {
        return FavoriteEventRepository.getInstance(application)
    }

    fun provideEventUseCase(context: Context): EventUseCase {
        val repository = FavoriteEventRepository.getInstance(context)
        return EventInteractor(repository)
    }

}
