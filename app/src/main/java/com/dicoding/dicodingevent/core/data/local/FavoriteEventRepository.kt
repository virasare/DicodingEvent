package com.dicoding.dicodingevent.core.data.local

import com.dicoding.dicodingevent.core.data.NetworkBoundResource
import com.dicoding.dicodingevent.core.data.Resource
import com.dicoding.dicodingevent.core.data.remote.ApiResponse
import com.dicoding.dicodingevent.core.data.remote.RemoteDataSource
import com.dicoding.dicodingevent.core.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.domain.repository.IEventRepository
import com.dicoding.dicodingevent.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteEventRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IEventRepository {

    override fun getAllFavoriteEvent(): Flow<List<Event>> {
        return localDataSource.getAllFavorite()
            .map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getFavoriteEventById(id: String): Flow<Event?> {
        return localDataSource.getFavoriteById(id)
            .map { entity -> entity?.let { DataMapper.mapEntityToDomain(it) } }
    }

    override suspend fun insertEvent(event: Event) {
        val entity = DataMapper.mapDomainToEntity(event)
        localDataSource.insertFavorite(entity)
    }

    override suspend fun delete(event: Event) {
        val entity = DataMapper.mapDomainToEntity(event)
        localDataSource.deleteFavorite(entity)
    }

    override fun getRemoteEvents(active: Int): Flow<Resource<List<Event>>> {
        return object : NetworkBoundResource<List<Event>, List<ListEventsItem>>() {
            override fun loadFromDB(): Flow<List<Event>> {
                return localDataSource.getAllFavorite()
                    .map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Event>?): Boolean {
                return true
            }

            override suspend fun fetchFromNetwork(): Flow<List<ListEventsItem>> {
                return remoteDataSource.getAllEvents(active).map {
                    when (it) {
                        is ApiResponse.Success -> it.data
                        else -> emptyList()
                    }
                }
            }

            override suspend fun saveCallResult(data: List<ListEventsItem>) {
            }
        }.asFlow()
    }

    companion object {
        @Volatile
        private var instance: FavoriteEventRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): FavoriteEventRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventRepository(remoteDataSource, localDataSource)
                    .also { instance = it }
            }
    }

}

