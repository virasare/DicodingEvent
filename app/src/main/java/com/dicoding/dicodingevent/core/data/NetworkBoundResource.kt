package com.dicoding.dicodingevent.core.data

import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow(): Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading()) // Emit loading state

        val dbData = loadFromDB().firstOrNull()

        if (shouldFetch(dbData)) {
            try {
                val apiResponse = fetchFromNetwork().first()
                saveCallResult(apiResponse)
                emitAll(
                    loadFromDB().map { Resource.Success(it) }
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        } else {
            emitAll(
                loadFromDB().map { Resource.Success(it) }
            )
        }
    }.catch { throwable ->
        emit(Resource.Error(throwable.message ?: "Something went wrong"))
    }

    protected abstract fun loadFromDB(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun fetchFromNetwork(): Flow<RequestType>
    protected abstract suspend fun saveCallResult(data: RequestType)
}