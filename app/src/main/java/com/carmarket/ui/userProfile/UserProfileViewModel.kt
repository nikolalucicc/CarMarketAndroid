package com.carmarket.ui.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.UserRepository
import com.carmarket.stateClasses.UserUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val userUIState: MutableStateFlow<UserUIState> = MutableStateFlow(UserUIState.Loading)
    val userDataState: StateFlow<UserUIState> = userUIState

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            userUIState.value = UserUIState.Loading
            runCatching {
                repository.getUserDetails(username)
            }.mapCatching {
                UserUIState.Success(it)
            }.getOrElse { exception ->
                UserUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                userUIState.value = state
            }
        }
    }

}