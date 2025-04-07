package com.dicoding.dicodingevent.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEventDao {

    @Query("SELECT * FROM FavoriteEventEntity ORDER BY id ASC")
    fun getAllFavorite(): Flow<List<FavoriteEventEntity>>

    @Query("SELECT * FROM FavoriteEventEntity WHERE id = :id")
    fun getFavoriteEventById(id: String): Flow<FavoriteEventEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(favoriteEventEntity: FavoriteEventEntity)

    @Delete
    suspend fun delete(favoriteEventEntity: FavoriteEventEntity)

}