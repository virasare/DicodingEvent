package com.dicoding.dicodingevent.core.domain.usecase

import com.dicoding.dicodingevent.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventUseCase {
    fun getAllFavoriteEvent(): Flow<List<Event>>
    fun getFavoriteEventById(id: String): Flow<Event?>
    suspend fun insertEvent(event: Event)
    suspend fun delete(event: Event)
}