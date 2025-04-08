package com.dicoding.dicodingevent.core.utils

import com.dicoding.dicodingevent.core.data.local.FavoriteEventEntity
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.data.remote.response.Event as RemoteEvent


object DataMapper {

    fun mapEntitiesToDomain(input: List<FavoriteEventEntity>): List<Event> =
        input.map {
            Event(
                id = it.id,
                name = it.name ?: "",
                imageLogo = it.imageLogo ?: "",
                isFavorite = true
            )
        }

    fun mapDomainToEntity(input: Event): FavoriteEventEntity =
        FavoriteEventEntity(
            id = input.id,
            name = input.name,
            imageLogo = input.imageLogo,
        )

    fun mapResponseToDomain(remote: RemoteEvent): Event {
        return Event(
            id = remote.id?.toString() ?: "",
            name = remote.name ?: "",
            imageLogo = remote.imageLogo ?: "",
            isFavorite = false
        )
    }

    fun mapEntityToDomain(entity: FavoriteEventEntity): Event =
        Event(
            id = entity.id,
            name = entity.name ?: "",
            imageLogo = entity.imageLogo ?: "",
            isFavorite = true
        )

}
