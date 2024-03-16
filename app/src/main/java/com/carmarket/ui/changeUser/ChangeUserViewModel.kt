package com.carmarket.ui.changeUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.model.request.UserRequest
import com.carmarket.repository.UserRepository
import kotlinx.coroutines.launch

class ChangeUserViewModel(
    private val repository: UserRepository
) : ViewModel(){

    fun updateUser(userRequest: UserRequest, newUsername: String, bearerToken: String) {
        viewModelScope.launch {
            try {
                repository.updateUser(userRequest, newUsername, bearerToken)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
