package com.dicoding.dicodingevent.core.domain.usecase

import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.domain.repository.IEventRepository

class EventInteractor(private val repository: IEventRepository): EventUseCase {
    override fun getAllFavoriteEvent() = repository.getAllFavoriteEvent()
    override fun getFavoriteEventById(id: String) = repository.getFavoriteEventById(id)
    override fun insertEvent(event: Event) = repository.insertEvent(event)
    override fun delete(event: Event) = repository.delete(event)
}