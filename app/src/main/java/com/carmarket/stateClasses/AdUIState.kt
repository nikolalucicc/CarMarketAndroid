package com.carmarket.stateClasses

import com.carmarket.model.responseBody.AdResponseBody

sealed class AdUIState {

    data object Loading: AdUIState()
    data class Success(val ads: List<AdResponseBody>) : AdUIState()
    data class Error(val message: String) : AdUIState()

}