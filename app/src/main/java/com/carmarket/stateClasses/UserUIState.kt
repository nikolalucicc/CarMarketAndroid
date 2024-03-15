package com.carmarket.stateClasses

import com.carmarket.model.responseBody.UserResponseBody

sealed class UserUIState {

    data object Loading: UserUIState()
    data class Success(val user: UserResponseBody) : UserUIState()
    data class Error(val message: String) : UserUIState()

}