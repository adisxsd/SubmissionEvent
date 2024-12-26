package com.dicoding.submissionevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionevent.data.response.ListEventsItem

class EventDetailViewModel : ViewModel() {
    private  val _event = MutableLiveData<ListEventsItem> ()
    val event : LiveData<ListEventsItem> get() = _event

    fun setEvent(eventData : ListEventsItem) {
        _event.value = eventData
    }
}