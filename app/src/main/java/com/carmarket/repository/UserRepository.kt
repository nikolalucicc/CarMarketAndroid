package com.carmarket.repository

import com.carmarket.model.request.LoginRequest
import com.carmarket.model.request.UserRequest
import com.carmarket.model.responseBody.LoginResponseBody
import com.carmarket.model.responseBody.UserResponseBody
import com.carmarket.network.CarMarketApi

class UserRepository(
    private var api: CarMarketApi
) {

    suspend fun registration(userRequest: UserRequest) {
        api.run {
            registration(userRequest)
        }
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponseBody =
        api.runCatching {
            login(loginRequest)
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun getUserDetails(username: String): UserResponseBody =
        api.runCatching {
            getUserDetails(username)
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

}