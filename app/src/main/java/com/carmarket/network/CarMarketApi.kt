package com.carmarket.network

import com.carmarket.model.responseBody.AdResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CarMarketApi {

    @GET("ad")
    suspend fun getAllAds(): Response<List<AdResponseBody>>
}