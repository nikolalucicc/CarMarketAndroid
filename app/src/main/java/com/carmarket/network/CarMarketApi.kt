package com.carmarket.network

import com.carmarket.model.responseBody.AdResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CarMarketApi {

    @GET("ad")
    suspend fun getAllAds(): Response<List<AdResponseBody>>

    @GET("ad/{id}")
    suspend fun getAdDetails(@Path("id") id: Long): Response<AdResponseBody>
}