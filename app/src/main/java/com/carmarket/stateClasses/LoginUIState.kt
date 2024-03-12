package com.carmarket.stateClasses

import com.carmarket.model.responseBody.LoginResponseBody

sealed class LoginUIState {
    data object Loading : LoginUIState()
    data class Success(val response: LoginResponseBody) : LoginUIState()
    data class Error(val message: String) : LoginUIState()
}