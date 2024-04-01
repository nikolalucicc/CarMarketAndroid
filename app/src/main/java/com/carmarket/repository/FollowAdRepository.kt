package com.carmarket.repository

import com.carmarket.model.request.FollowAdRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.model.responseBody.FollowAdResponseBody
import com.carmarket.network.CarMarketApi

class FollowAdRepository(
    private val api: CarMarketApi
) {
    suspend fun followAd(followAdRequest: FollowAdRequest, bearerToken: String?): FollowAdResponseBody =
        api.runCatching {
             followAd(followAdRequest, "Bearer $bearerToken")
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun getFollowingAds(id: Long, bearerToken: String?): List<FollowAdResponseBody> =
        api.runCatching {
            getFollowingAds(id, "Bearer $bearerToken")
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun deleteFromFavorites(userId: Long, adId: Long, bearerToken: String?): Unit =
        api.runCatching {
            deleteFromFavorites(userId, adId, "Bearer $bearerToken")
        }.getOrElse {
            throw it
        }
}