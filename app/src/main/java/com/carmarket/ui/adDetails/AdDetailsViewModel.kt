package com.carmarket.ui.adDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.AdDetailsRepository
import com.carmarket.stateClasses.AdDetailsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdDetailsViewModel(
    private val repository: AdDetailsRepository
) : ViewModel() {

    private val adDetailsUIState: MutableStateFlow<AdDetailsUIState> = MutableStateFlow(AdDetailsUIState.Loading)
    val adDetailsDataState: StateFlow<AdDetailsUIState> = adDetailsUIState

    fun getAdDetails(id: Long){
        viewModelScope.launch {
            adDetailsUIState.value = AdDetailsUIState.Loading
            runCatching {
                repository.getAdDetails(id)
            }.mapCatching {
                AdDetailsUIState.Success(it)
            }.getOrElse{ exception ->
                AdDetailsUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                adDetailsUIState.value = state
            }
        }
    }

}