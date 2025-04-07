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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class DetailViewModel @Inject constructor(private val eventUseCase: EventUseCase) : ViewModel() {

    private val _eventDetail = MutableLiveData<RemoteEvent?>()
    val eventDetail: LiveData<RemoteEvent?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun fetchEventDetails(eventId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: DetailEventResponse =
                    ApiConfig.getApiService().getDetailEvent(eventId.toString())
                _eventDetail.value = response.event
            } catch (e: IOException) {
                _errorMessage.value = "Network Error: ${e.message}"
            } catch (e: HttpException) {
                _errorMessage.value = "Server Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkFavoriteStatus(eventId: String) {
        viewModelScope.launch {
            eventUseCase.getFavoriteEventById(eventId).collect { entity ->
                _isFavorite.value = entity != null
            }
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