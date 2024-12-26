package com.dicoding.submissionevent.data.retrofit

import com.dicoding.submissionevent.data.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): EventResponse


    @GET("events")
    suspend fun getUpcomingEvents(): EventResponse {
        return getEvents(active = 1)
    }

    @GET("events")
    suspend fun getPastEvents(): EventResponse {
        return getEvents(active = 0)
    }

    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: String): EventResponse
}
