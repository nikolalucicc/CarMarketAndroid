package com.carmarket.stateClasses

import com.carmarket.model.responseBody.FollowAdResponseBody

sealed class NewFollowAdUIState {

    data object Loading: NewFollowAdUIState()
    data class Success(val followAd: FollowAdResponseBody): NewFollowAdUIState()
    data class Error(val message: String): NewFollowAdUIState()

}