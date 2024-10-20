package com.dicoding.dicodingevent.data.remote.retrofit

import com.dicoding.dicodingevent.data.remote.response.EventResponse
import retrofit2.http.*
import com.dicoding.dicodingevent.data.remote.response.DetailEventResponse
import retrofit2.Call

interface ApiService {

    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<EventResponse>

//    //search the events
//    @GET("events")
//    fun searchEvents(
//        @Query("active") active: Int,
//        @Query("q") keyword: String,
//    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String
    ): Call<DetailEventResponse>
}