package com.dicoding.submissionevent.repository

import com.dicoding.submissionevent.database.FavoriteEvent
import com.dicoding.submissionevent.database.FavoriteEventDao
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteEventDao) {
    fun getFavorites(): Flow<List<FavoriteEvent>> = dao.getFavorites()

    suspend fun addFavorite(event: FavoriteEvent) {
        dao.addFavorite(event)
    }

    suspend fun removeFavorite(eventId: Int): Int {
        return dao.removeFavorite(eventId)
    }

    suspend fun isFavorite(eventId: Int): Boolean {
        val count = dao.isFavorite(eventId)
        return count > 0
    }
}