package com.dicoding.dicodingevent.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.core.data.remote.response.EventResponse
import com.dicoding.dicodingevent.core.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    private val _isUpcomingLoading = MutableLiveData<Boolean>()
    val isUpcomingLoading: LiveData<Boolean> = _isUpcomingLoading

    private val _isFinishedLoading = MutableLiveData<Boolean>()
    val isFinishedLoading: LiveData<Boolean> = _isFinishedLoading

    fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _isUpcomingLoading.value = true
            try {
                val response: EventResponse = ApiConfig.getApiService().getEvents(1)
                _upcomingEvents.value = response.listEvents.take(5)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            } finally {
                _isUpcomingLoading.value = false
            }
        }
    }

    fun fetchFinishedEvents() {
        viewModelScope.launch {
            _isFinishedLoading.value = true
            try {
                val response: EventResponse = ApiConfig.getApiService().getEvents(0)
                _finishedEvents.value = response.listEvents.take(5)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            } finally {
                _isFinishedLoading.value = false
            }
        }
    }
}