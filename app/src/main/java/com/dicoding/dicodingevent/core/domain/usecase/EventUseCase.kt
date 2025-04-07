package com.dicoding.dicodingevent.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dicoding.dicodingevent.core.domain.model.Event

interface EventUseCase {
    fun getAllFavoriteEvent(): LiveData<List<Event>>
    fun getFavoriteEventById(id: String): LiveData<Event?>
    fun insertEvent(event: Event)
    fun delete(event: Event)
}