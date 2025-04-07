package com.dicoding.dicodingevent.ui.fragments.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.core.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    fun fetchEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(1) // 1 for upcoming events
                _events.value = response.listEvents
            } catch (e: Exception) {
                _snackbarText.value = "Koneksi gagal: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}