package com.dicoding.dicodingevent.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.dicodingevent.data.database.Event
import com.dicoding.dicodingevent.data.retrofit.ApiService
import com.dicoding.dicodingevent.data.database.EventDao
import com.dicoding.dicodingevent.data.Result


class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
){

    fun getEvent(): LiveData<Result<List<Event>>> = liveData {
        emit(Result.Loading)
//        try {
//            val response = apiService.getEvents(BuildConfig.API_KEY)
//            val events = response.listEvents // Pastikan 'response' memiliki property 'events'
//            val eventsList = events.map { eventItem ->
//                val isFavorited = eventDao.isEventFavorited(events.id)
//                Event(
//                    id = events.id,
//                    name = events.name,
//                    summary = events.summary,
//                    imageLogo = events.imageLogo,
//                    isFavorited = false
//                )
//            }
//            eventDao.deleteAll()
//            eventDao.insertEvent(eventsList)
//            emit(Result.Success(eventsList)) // Emit Success setelah berhasil
//        } catch (e: Exception) {
//            Log.d("EventRepository", "getEvent: ${e.message}")
//            emit(Result.Error(e.message.toString())) // Pastikan menggunakan Result.Error
//        }
//        val localData: LiveData<Result<List<Event>>> = eventDao.getAllEvent().map { Result.Success(it) }
//        emitSource(localData)
    }


    fun getFavoriteEvents(): LiveData<List<Event>> {
        return eventDao.getFavoriteEvents()
    }

    suspend fun setEventFavorite(event: Event, favoriteState: Boolean) {
        event.isFavorited = favoriteState
        eventDao.updateEvent(event)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}