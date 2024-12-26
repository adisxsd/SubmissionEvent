package com.dicoding.submissionevent.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEvent)

    @Query("DELETE FROM favorite_event WHERE id = :eventId")
    suspend fun removeFavorite(eventId: Int): Int

    @Query("SELECT * FROM favorite_event")
    fun getFavorites(): Flow<List<FavoriteEvent>>

    @Query("SELECT COUNT(*) FROM favorite_event WHERE id = :eventId")
    suspend fun isFavorite(eventId: Int): Int
}
