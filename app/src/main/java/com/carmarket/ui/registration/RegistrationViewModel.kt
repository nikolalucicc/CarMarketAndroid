package com.carmarket.ui.registration

import androidx.lifecycle.ViewModel
import com.carmarket.model.request.UserRequest
import com.carmarket.repository.UserRepository

class RegistrationViewModel(
    private val repository: UserRepository
) : ViewModel() {
    suspend fun registration(userRequest: UserRequest) {
        repository.registration(userRequest)
    }
}