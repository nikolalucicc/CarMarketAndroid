package com.carmarket.ui.removeAd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.RemoveAdRepository
import kotlinx.coroutines.launch

class RemoveAdViewModel(
    private val repository: RemoveAdRepository
) : ViewModel() {

    suspend fun removeAd(id: Long, bearerToken: String) {
        viewModelScope.launch {
            runCatching {
                repository.removeAd(id, bearerToken)
            }.onFailure { exception ->
                val errorMessage = exception.message ?: "Unknown error"
                Log.e("FollowAdViewModel", "Error deleting ad from favorites: $errorMessage")
            }
        }
    }

}