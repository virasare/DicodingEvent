package com.dicoding.dicodingevent.core.di


import android.content.Context
import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository

object Injection {
    fun provideRepository(application: Context): FavoriteEventRepository {
        return FavoriteEventRepository.getInstance(application)
    }
}
