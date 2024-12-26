package com.dicoding.submissionevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class completedEventsVM : ViewModel() {
    private val _events = MutableLiveData<List<ListEventsItem>> ()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val  errorMessage:LiveData<String?> = _errorMessage

    fun loadCompletedEvents() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getEvents(active = 0)
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    if (response.error) {
                        _errorMessage.value = "Failed to load events: ${response.message}"
                    } else {
                        _events.value = response.listEvents
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _errorMessage.value = "Error: ${e.message}"
                }
            }
        }
    }
}