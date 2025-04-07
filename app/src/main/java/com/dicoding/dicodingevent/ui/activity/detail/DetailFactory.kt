package com.dicoding.dicodingevent.ui.activity.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase

class DetailFactory(private val eventUseCase: EventUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(eventUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}