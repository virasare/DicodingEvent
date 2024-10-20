package com.dicoding.dicodingevent.di


import android.content.Context
import com.dicoding.dicodingevent.data.local.FavoriteEventRepository

object Injection {
    fun provideRepository(application: Context): FavoriteEventRepository {
        return FavoriteEventRepository.getInstance(application)
    }
}
