package com.dicoding.dicodingevent.core.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteEventDao: FavoriteEventDao) {

    fun getAllFavorite(): Flow<List<FavoriteEventEntity>> = favoriteEventDao.getAllFavorite()

    fun getFavoriteById(id: String): Flow<FavoriteEventEntity?> = favoriteEventDao.getFavoriteEventById(id)

    suspend fun insertFavorite(event: FavoriteEventEntity) = favoriteEventDao.insertEvent(event)

    suspend fun deleteFavorite(event: FavoriteEventEntity) = favoriteEventDao.delete(event)

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource {
            val database = FavoriteEventDatabase.getDatabase(context)
            return instance ?: synchronized(this) {
                instance ?: LocalDataSource(database.favoriteEventDao()).also { instance = it }
            }
        }
    }
}