package com.dicoding.dicodingevent.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.data.remote.response.DetailEventResponse
import com.dicoding.dicodingevent.core.data.remote.response.Event as RemoteEvent
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingevent.core.domain.usecase.EventUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val eventUseCase: EventUseCase) : ViewModel() {

    private val _eventDetail = MutableLiveData<RemoteEvent?>()
    val eventDetail: LiveData<RemoteEvent?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchEventDetails(eventId: Int) {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService()
        apiService.getDetailEvent(eventId.toString()).enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(call: Call<DetailEventResponse>, response: Response<DetailEventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val remoteEvent: RemoteEvent? = response.body()?.event
                    _eventDetail.value = remoteEvent
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Failure: ${t.message}"
            }
        })
    }

    fun checkFavoriteStatus(eventId: String) {
        eventUseCase.getFavoriteEventById(eventId).observeForever { entity ->
            _isFavorite.value = entity != null
        }
    }

    fun addToFavorite(event: Event) {
        viewModelScope.launch {
            eventUseCase.insertEvent(event)
            _isFavorite.value = true
        }
    }

    fun deleteFromFavorite(event: Event) {
        viewModelScope.launch {
            eventUseCase.delete(event)
            _isFavorite.value = false
        }
    }
}
