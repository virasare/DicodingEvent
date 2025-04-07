package com.dicoding.dicodingevent.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase

class FavoriteViewModel(private val eventUseCase: EventUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _favoriteEvent = MutableLiveData<List<Event>>()
    val favoriteEvent: LiveData<List<Event>> get() = _favoriteEvent

    init {
        loadFavoriteEvents()
    }

    private fun loadFavoriteEvents() {
        _isLoading.value = true

        eventUseCase.getAllFavoriteEvent().observeForever { events ->
            _isLoading.value = false
            _favoriteEvent.value = events
        }
    }
}