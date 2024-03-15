package com.carmarket.repository

import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.network.CarMarketApi

class AdsByUserRepository(
    private var api: CarMarketApi
) {
    suspend fun getAdsByUser(id: Long, bearerToken: String?): List<AdResponseBody> =
        api.runCatching {
            getAdsByUser(id, "Bearer $bearerToken")
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()
}