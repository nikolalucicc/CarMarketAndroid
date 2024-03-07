package com.carmarket.repository

import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.network.CarMarketApi

class AllAdsRepository(
    private var api: CarMarketApi,
) {
    suspend fun getAllAds(): List<AdResponseBody> =
        api.runCatching {
            getAllAds()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()
}