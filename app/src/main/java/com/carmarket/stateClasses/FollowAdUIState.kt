package com.carmarket.stateClasses

import com.carmarket.model.responseBody.FollowAdResponseBody

sealed class FollowAdUIState {

    data object Loading: FollowAdUIState()
    data class Success(val followAd: List<FollowAdResponseBody>): FollowAdUIState()
    data class Error(val message: String): FollowAdUIState()

}