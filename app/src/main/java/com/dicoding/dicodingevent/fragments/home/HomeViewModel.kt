package com.dicoding.dicodingevent.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.data.response.EventResponse
import com.dicoding.dicodingevent.data.response.ListEventsItem
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        _isUpcomingLoading.value = true
        val client = ApiConfig.getApiService().getEvents(1) // 1 untuk upcoming events
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isUpcomingLoading.value = false
                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body()?.listEvents?.take(5) ?: emptyList()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isUpcomingLoading.value = false
            }
        })
    }

    fun fetchFinishedEvents() {
        _isFinishedLoading.value = true
        val client = ApiConfig.getApiService().getEvents(0) // 0 untuk finished events
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isFinishedLoading.value = false
                if (response.isSuccessful) {
                    _finishedEvents.value = response.body()?.listEvents?.take(5) ?: emptyList()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isFinishedLoading.value = false
            }
        })
    }
}