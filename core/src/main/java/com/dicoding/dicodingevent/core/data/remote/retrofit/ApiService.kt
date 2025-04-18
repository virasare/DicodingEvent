package com.dicoding.dicodingevent.core.data.remote.retrofit

import com.dicoding.dicodingevent.core.data.remote.response.DetailEventResponse
import com.dicoding.dicodingevent.core.data.remote.response.EventResponse
import retrofit2.http.*

interface ApiService {

    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int
    ): EventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: String
    ): DetailEventResponse
}