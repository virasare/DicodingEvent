package com.dicoding.dicodingevent.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val eventUseCase: EventUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _favoriteEvent = MutableLiveData<List<Event>>()
    val favoriteEvent: LiveData<List<Event>> get() = _favoriteEvent

    init {
        loadFavoriteEvents()
    }

    private fun loadFavoriteEvents() {
        _isLoading.value = true

        viewModelScope.launch {
            eventUseCase.getAllFavoriteEvent()
                .collect { events ->
                    _isLoading.postValue(false)
                    _favoriteEvent.postValue(events)
                }
        }
    }
}
