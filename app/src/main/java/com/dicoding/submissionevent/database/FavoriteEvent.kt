package com.dicoding.submissionevent.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
data class FavoriteEvent(
    @PrimaryKey val id: Int,
    val name: String,
    val mediaCover: String,
    val beginTime: String,
    val endTime: String,
    val registrants: Int,
    val description: String,
    val ownerName: String,
    val quota: Int,
    val link: String
)
