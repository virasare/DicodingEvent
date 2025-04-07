package com.dicoding.dicodingevent.core.data.local

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.content.Context
import androidx.lifecycle.map
import com.dicoding.dicodingevent.core.domain.model.Event
import com.dicoding.dicodingevent.core.utils.DataMapper

class FavoriteEventRepository(context: Context) {
    private val favoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventDatabase.getDatabase(context)
        favoriteEventDao = db.favoriteEventDao()
    }

    fun getAllFavoriteEvent(): LiveData<List<Event>> {
        return favoriteEventDao.getAllFavorite()
            .map { DataMapper.mapEntitiesToDomain(it) }
    }

    fun insertEvent(event: Event) {
        val entity = DataMapper.mapDomainToEntity(event)
        executorService.execute { favoriteEventDao.insertEvent(entity) }
    }

    fun delete(event: Event) {
        val entity = DataMapper.mapDomainToEntity(event)
        executorService.execute { favoriteEventDao.delete(entity) }
    }

    fun getFavoriteEventById(id: String): LiveData<Event?> {
        return favoriteEventDao.getFavoriteEventById(id)
            .map { entity ->
                entity?.let {
                    DataMapper.mapEntitiesToDomain(listOf(it)).firstOrNull()
                }
            }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: FavoriteEventRepository? = null

        // Ensure this method only takes Application as a parameter
        fun getInstance(context: Context): FavoriteEventRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventRepository(context)
            }.also { instance = it }
    }
}