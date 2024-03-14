package com.carmarket.repository

import com.carmarket.model.request.AdRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.network.CarMarketApi

class CreateAdRepository(
    private val api: CarMarketApi
) {
    suspend fun createAd(adRequest: AdRequest, bearerToken: String?): AdResponseBody =
        api.runCatching {
            createAd(adRequest, "Bearer $bearerToken")
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()
}