package com.dicoding.submissionevent.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.database.AppDatabase
import com.dicoding.submissionevent.database.FavoriteEvent
import com.dicoding.submissionevent.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    val favoriteEvents: LiveData<List<ListEventsItem>>

    init {
        val dao = AppDatabase.getDatabase(application).favoriteEventDao()
        repository = FavoriteRepository(dao)

        favoriteEvents = repository.getFavorites()
            .asLiveData()
            .map { favoriteEvents ->
                favoriteEvents.map { favoriteEvent ->
                    ListEventsItem(
                        id = favoriteEvent.id,
                        name = favoriteEvent.name,
                        mediaCover = favoriteEvent.mediaCover,
                        beginTime = favoriteEvent.beginTime,
                        endTime = favoriteEvent.endTime,
                        registrants = favoriteEvent.registrants,
                        description = favoriteEvent.description,
                        ownerName = favoriteEvent.ownerName,
                        quota = favoriteEvent.quota,
                        link = favoriteEvent.link,
                        imageLogo = "",
                        cityName = "",
                        category = "",
                        summary = "'",
                    )
                }
            }
    }

    fun addFavorite(event: FavoriteEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(event)
        }
    }
    fun removeFavorite(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val rowsDeleted = repository.removeFavorite(eventId)
            if (rowsDeleted > 0) {
                launch(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Deleted", Toast.LENGTH_SHORT).show()
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Failed to delete", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun isFavorite(eventId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.isFavorite(eventId)
            callback(result)
        }
    }
}
