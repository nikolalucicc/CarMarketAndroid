package com.carmarket.ui.adDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.AdDetailsRepository
import com.carmarket.stateClasses.OneAdUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdDetailsViewModel(
    private val repository: AdDetailsRepository
) : ViewModel() {

    private val oneAdUIState: MutableStateFlow<OneAdUIState> = MutableStateFlow(OneAdUIState.Loading)
    val adDetailsDataState: StateFlow<OneAdUIState> = oneAdUIState

    fun getAdDetails(id: Long){
        viewModelScope.launch {
            oneAdUIState.value = OneAdUIState.Loading
            runCatching {
                repository.getAdDetails(id)
            }.mapCatching {
                OneAdUIState.Success(it)
            }.getOrElse{ exception ->
                OneAdUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                oneAdUIState.value = state
            }
        }
    }

}