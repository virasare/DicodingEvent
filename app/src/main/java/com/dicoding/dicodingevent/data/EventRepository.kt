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

//    fun loadFinishedEvents(): LiveData<Result<List<ListEventsItem>>> = liveData {
//        emit(Result.Loading)
//        try {
//            // Assuming getEvents(0) returns an EventResponse
//            val response = apiService.getEvents(0) // Make sure getEvents returns EventResponse
//            val events = response.li // Accessing the listEvents property
//            emit(Result.Success(events)) // Emit the list directly
//        } catch (e: Exception) {
//            Log.e("EventRepository", "Error loading finished events", e)
//            emit(Result.Error(e.message.toString()))
//        }
//    }

//    fun loadUpcomingEvents(): LiveData<Result<List<ListEventsItem>>> = liveData {
//        emit(Result.Loading)
//        try {
//            // Assuming getEvents(1) returns an EventResponse
//            val response: EventResponse = apiService.getEvents(1) // Make sure getEvents returns EventResponse
//            val events = response.listEvents // Accessing the listEvents property
//            emit(Result.Success(events)) // Emit the list directly
//        } catch (e: Exception) {
//            Log.e("EventRepository", "Error loading upcoming events", e)
//            emit(Result.Error(e.message.toString()))
//        }
//    }
//
//    fun detailEvent(id: Int): LiveData<Result<ListEventsItem>> = liveData { // Change to Int to match id type
//        emit(Result.Loading)
//        try {
//            // Assuming getEvents(id) returns an EventResponse for a single event
//            val response: EventResponse = apiService.getEvents(id) // Make sure getEvents returns EventResponse
//            val event = response.listEvents.find { it.id == id } // Find the event by ID
//            if (event != null) {
//                emit(Result.Success(event))
//            } else {
//                emit(Result.Error("Event not found"))
//            }
//        } catch (e: Exception) {
//            Log.e("EventRepository", "Error loading event details", e)
//            emit(Result.Error(e.message.toString()))
//        }
//    }

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
