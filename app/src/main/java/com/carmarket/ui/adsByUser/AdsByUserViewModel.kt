package com.carmarket.ui.adsByUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.AdsByUserRepository
import com.carmarket.stateClasses.AdUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdsByUserViewModel(
    private val repository: AdsByUserRepository
) : ViewModel() {

    private val adUIState: MutableStateFlow<AdUIState> = MutableStateFlow(AdUIState.Loading)
    val adUiDataState: StateFlow<AdUIState> = adUIState

    fun getAdsByUser(id: Long, bearerToken: String?) {
        viewModelScope.launch {
            adUIState.value = AdUIState.Loading
            runCatching {
                repository.getAdsByUser(id, bearerToken)
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
