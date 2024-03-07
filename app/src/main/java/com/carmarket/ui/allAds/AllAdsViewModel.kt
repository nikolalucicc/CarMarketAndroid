package com.carmarket.ui.allAds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.AllAdsRepository
import com.carmarket.stateClasses.AdUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllAdsViewModel(
    private val repository: AllAdsRepository
) : ViewModel() {

    private val adUIState: MutableStateFlow<AdUIState> = MutableStateFlow(AdUIState.Loading)
    val adUiDataState: StateFlow<AdUIState> = adUIState

    fun getAllAds() {
        viewModelScope.launch {
            adUIState.value = AdUIState.Loading
            runCatching {
                repository.getAllAds()
            }.mapCatching { ads ->
                AdUIState.Success(ads)
            }.getOrElse { exception ->
                AdUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                adUIState.value = state
            }
        }
    }

}