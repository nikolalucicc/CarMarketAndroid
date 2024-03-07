package com.carmarket.stateClasses

import com.carmarket.model.responseBody.AdResponseBody

sealed class AdDetailsUIState {

    data object Loading: AdDetailsUIState()
    data class Success(val ad: AdResponseBody) : AdDetailsUIState()
    data class Error(val message: String) : AdDetailsUIState()

}