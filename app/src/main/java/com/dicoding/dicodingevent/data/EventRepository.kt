package com.dicoding.dicodingevent.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.dicodingevent.data.local.FavoriteEventDao
import com.dicoding.dicodingevent.data.local.FavoriteEventEntity
import com.dicoding.dicodingevent.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao
) {

    fun getAllFavorite(): LiveData<List<FavoriteEventEntity>> {
        return favoriteEventDao.getAllFavorite()
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: FavoriteEventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}