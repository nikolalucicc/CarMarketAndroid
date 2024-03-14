package com.carmarket.stateClasses

import com.carmarket.model.responseBody.AdResponseBody

sealed class OneAdUIState {

    data object Loading: OneAdUIState()
    data class Success(val ad: AdResponseBody) : OneAdUIState()
    data class Error(val message: String) : OneAdUIState()

}