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

    suspend fun sortByYearDesc(): List<AdResponseBody> =
        api.runCatching {
            sortByYearDesc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByYearAsc(): List<AdResponseBody> =
        api.runCatching {
            sortByYearAsc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByPriceDesc(): List<AdResponseBody> =
        api.runCatching {
            sortByPriceDesc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByPriceAsc(): List<AdResponseBody> =
        api.runCatching {
            sortByPriceAsc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByMileageDesc(): List<AdResponseBody> =
        api.runCatching {
            sortByMileageDesc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByMileageAsc(): List<AdResponseBody> =
        api.runCatching {
            sortByMileageAsc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByPostedDateDesc(): List<AdResponseBody> =
        api.runCatching {
            sortByPostedDateDesc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

    suspend fun sortByPostedDateAsc(): List<AdResponseBody> =
        api.runCatching {
            sortByPostedDateAsc()
        }.mapCatching {
            requireNotNull(it.body()) { "The response body is null." }
        }.getOrThrow()

}