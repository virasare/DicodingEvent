package com.dicoding.dicodingevent.core.domain.usecase

import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.domain.repository.IEventRepository
import kotlinx.coroutines.flow.Flow

class EventInteractor(private val repository: IEventRepository): EventUseCase {
    override fun getAllFavoriteEvent(): Flow<List<Event>> =
        repository.getAllFavoriteEvent()

    override fun getFavoriteEventById(id: String): Flow<Event?> =
        repository.getFavoriteEventById(id)

    override suspend fun insertEvent(event: Event) {
        repository.insertEvent(event)
    }

    override suspend fun delete(event: Event) {
        repository.delete(event)
    }
}