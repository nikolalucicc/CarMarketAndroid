package com.carmarket.repository

import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.network.CarMarketApi

class AdDetailsRepository(
    private var api: CarMarketApi
) {
    suspend fun getAdDetails(id: Long): AdResponseBody =
        api.runCatching {
            getAdDetails(id)
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()
}