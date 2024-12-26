package com.dicoding.submissionevent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.submissionevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class UpcomingEventsViewModel : ViewModel() {

    val upcomingEvents = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            val response = ApiConfig.getApiService().getEvents(active = 1)
            if (response.error) {
                emit(Resource.Error("Failed to load events: ${response.message}"))
            } else {
                emit(Resource.Success(response.listEvents))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}
