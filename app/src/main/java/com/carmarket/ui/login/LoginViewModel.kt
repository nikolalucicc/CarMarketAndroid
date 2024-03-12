package com.carmarket.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.model.request.LoginRequest
import com.carmarket.repository.UserRepository
import com.carmarket.stateClasses.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState.Loading)
    val loginUiDataState: StateFlow<LoginUIState> = loginUIState

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUIState.value = LoginUIState.Loading
            runCatching {
                repository.login(loginRequest)
            }.fold(
                onSuccess = { response ->
                    loginUIState.value = LoginUIState.Success(response)
                },
                onFailure = { exception ->
                    loginUIState.value = LoginUIState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }
}