package com.carmarket.ui.createAd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.model.request.AdRequest
import com.carmarket.repository.CreateAdRepository
import com.carmarket.stateClasses.OneAdUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateAdViewModel(
    private val repository: CreateAdRepository
) : ViewModel(){

    private val adUIState: MutableStateFlow<OneAdUIState> = MutableStateFlow(OneAdUIState.Loading)

    fun createAd(adRequest: AdRequest, bearerToken: String?) {
        viewModelScope.launch {
            adUIState.value = OneAdUIState.Loading
            runCatching {
                repository.createAd(adRequest, bearerToken)
            }.fold(
                onSuccess = { response ->
                    adUIState.value = OneAdUIState.Success(response)
                },
                onFailure = { exception ->
                    adUIState.value = OneAdUIState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }
}

