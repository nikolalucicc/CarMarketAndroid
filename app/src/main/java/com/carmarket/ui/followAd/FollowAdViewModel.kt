package com.carmarket.ui.followAd

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.model.request.FollowAdRequest
import com.carmarket.repository.FollowAdRepository
import com.carmarket.stateClasses.AdUIState
import com.carmarket.stateClasses.FollowAdUIState
import com.carmarket.stateClasses.NewFollowAdUIState
import com.carmarket.utils.TokenUtil.getUserIdFromToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowAdViewModel(
    private val repository: FollowAdRepository
) : ViewModel() {

    private val newFollowAdUIState: MutableStateFlow<NewFollowAdUIState> = MutableStateFlow(NewFollowAdUIState.Loading)

    private val followAdUIState: MutableStateFlow<FollowAdUIState> = MutableStateFlow(FollowAdUIState.Loading)
    val followAdUiDataState: StateFlow<FollowAdUIState> = followAdUIState

    fun followAd(followAdRequest: FollowAdRequest, bearerToken: String){
        viewModelScope.launch {
            newFollowAdUIState.value = NewFollowAdUIState.Loading
            runCatching {
                repository.followAd(followAdRequest, bearerToken)
            }.fold(
                onSuccess = {response ->
                    newFollowAdUIState.value = NewFollowAdUIState.Success(response)
                },
                onFailure = {exception ->
                    newFollowAdUIState.value = NewFollowAdUIState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }

    fun getFollowingAds(id: Long, bearerToken: String) {
        viewModelScope.launch {
            followAdUIState.value = FollowAdUIState.Loading
            runCatching {
                repository.getFollowingAds(id, bearerToken)
            }.mapCatching { ads ->
                FollowAdUIState.Success(ads)
            }.getOrElse { exception ->
                FollowAdUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                followAdUIState.value = state
            }
        }
    }

    fun deleteFromFavorites(userId: Long, adId: Long, bearerToken: String) {
        viewModelScope.launch {
            runCatching {
                repository.deleteFromFavorites(userId, adId, bearerToken)
            }.onFailure { exception ->
                val errorMessage = exception.message ?: "Unknown error"
                Log.e("FollowAdViewModel", "Error deleting ad from favorites: $errorMessage")
            }
        }
    }

    fun isAdFollowed(adId: Long): Boolean {
        return when (val currentState = followAdUiDataState.value) {
            is FollowAdUIState.Success -> {
                val ads = currentState.followAd
                ads.any { it.ad.id == adId }
            }
            else -> false
        }
    }

}