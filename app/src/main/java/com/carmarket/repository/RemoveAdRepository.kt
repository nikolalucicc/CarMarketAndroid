package com.carmarket.repository

import com.carmarket.network.CarMarketApi

class RemoveAdRepository(
    private val api: CarMarketApi
) {

    suspend fun removeAd(id: Long, bearerToken: String?) : Unit =
        api.runCatching {
            removeAd(id, "Bearer $bearerToken")
        }.getOrElse {
            throw it
        }

}