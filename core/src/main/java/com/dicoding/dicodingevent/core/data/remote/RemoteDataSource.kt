package com.dicoding.dicodingevent.core.data.remote

import android.util.Log
import com.dicoding.dicodingevent.core.data.remote.response.Event
import com.dicoding.dicodingevent.core.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.core.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getAllEvents(active: Int): Flow<ApiResponse<List<ListEventsItem>>> = flow {
        try {
            val response = apiService.getEvents(active)
            val dataList = response.listEvents

            if (dataList.isNotEmpty()) {
                emit(ApiResponse.Success(dataList))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", "getAllEvents: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailEvent(id: String): Flow<ApiResponse<Event>> = flow {
        try {
            val response = apiService.getDetailEvent(id)
            val data = response.event

            if (data != null) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", "getDetailEvent: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}