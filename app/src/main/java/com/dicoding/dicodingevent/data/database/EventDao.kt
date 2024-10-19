package com.dicoding.dicodingevent.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<List<Event>>

    @Query("SELECT * FROM events WHERE favorited = 1")
    fun getFavoriteEvents(): LiveData<List<Event>>

    @Query("SELECT * from events ORDER BY id ASC")
    fun getAllEvent(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(news: List<Event>)

    @Query("DELETE FROM events WHERE favorited = 0")
    suspend fun deleteAll()

//    @Query("UPDATE events SET favorited = 1 WHERE id = :id")
//    suspend fun addEventToFavorites(id: Int)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("UPDATE events SET favorited = 0 WHERE id = :id")
    suspend fun removeEventFromFavorites(id: Int)

    @Query("SELECT favorited FROM events WHERE id = :id")
    fun isEventFavorited(id: Int): LiveData<Boolean>

}