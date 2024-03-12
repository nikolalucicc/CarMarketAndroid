package com.carmarket.repository

import com.carmarket.model.request.UserRequest
import com.carmarket.network.CarMarketApi

class UserRepository(
    private var api: CarMarketApi
) {
    suspend fun registration(userRequest: UserRequest) {
        api.run {
            registration(userRequest)
        }
    }
}