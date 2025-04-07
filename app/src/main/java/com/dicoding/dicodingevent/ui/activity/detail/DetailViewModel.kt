package com.dicoding.dicodingevent.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.core.data.local.FavoriteEventEntity
import com.dicoding.dicodingevent.core.data.local.FavoriteEventRepository
import kotlinx.coroutines.launch
import com.dicoding.dicodingevent.core.data.remote.response.DetailEventResponse
import com.dicoding.dicodingevent.core.data.remote.response.Event
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(private val repository: FavoriteEventRepository) : ViewModel() {

    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> = _eventDetail

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
                    _eventDetail.value = response.body()?.event
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
        repository.getFavoriteEventById(eventId).observeForever { favoriteEvent ->
            _isFavorite.value = favoriteEvent != null
        }
    }

    fun addToFavorite(event: Event) {
        val favoriteEventEntity = FavoriteEventEntity(
            id = event.id.toString(),
            name = event.name,
            imageLogo = event.imageLogo
        )

        viewModelScope.launch {
            repository.insertEvent(favoriteEventEntity)
            _isFavorite.value = true
        }
    }

    fun deleteFromFavorite(eventId: String) {
        val favoriteEventEntity = FavoriteEventEntity(id = eventId)

        viewModelScope.launch {
            repository.delete(favoriteEventEntity)
            _isFavorite.value = false
        }
    }

}