package com.dicoding.dicodingevent.core.domain.repository

import com.dicoding.dicodingevent.core.data.Resource
import com.dicoding.dicodingevent.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface IEventRepository {

    fun getAllFavoriteEvent(): Flow<List<Event>>
    fun getFavoriteEventById(id: String): Flow<Event?>
    suspend fun insertEvent(event: Event)
    suspend fun delete(event: Event)

    fun getRemoteEvents(active: Int): Flow<Resource<List<Event>>>

}