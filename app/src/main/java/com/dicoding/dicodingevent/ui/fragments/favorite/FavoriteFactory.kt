package com.dicoding.dicodingevent.ui.fragments.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.core.di.Injection
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase

class FavoriteFactory private constructor(private val eventUseCase: EventUseCase) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(eventUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteFactory? = null
        fun getInstance(context: Context): FavoriteFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteFactory(Injection.provideEventUseCase(context))
            }.also { instance = it }
    }
}