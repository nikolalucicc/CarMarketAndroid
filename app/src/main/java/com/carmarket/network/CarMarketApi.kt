package com.carmarket.network

import com.carmarket.model.request.UserRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.model.responseBody.UserResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarMarketApi {

    @GET("ad")
    suspend fun getAllAds(): Response<List<AdResponseBody>>

    @GET("ad/sortByYearDesc")
    suspend fun sortByYearDesc(): Response<List<AdResponseBody>>

    @GET("ad/sortByYearAsc")
    suspend fun sortByYearAsc(): Response<List<AdResponseBody>>

    @GET("ad/sortByPriceDesc")
    suspend fun sortByPriceDesc(): Response<List<AdResponseBody>>

    @GET("ad/sortByPriceAsc")
    suspend fun sortByPriceAsc(): Response<List<AdResponseBody>>

    @GET("ad/sortByMileageDesc")
    suspend fun sortByMileageDesc(): Response<List<AdResponseBody>>

    @GET("ad/sortByMileageAsc")
    suspend fun sortByMileageAsc(): Response<List<AdResponseBody>>

    @GET("ad/sortByPostedDateDesc")
    suspend fun sortByPostedDateDesc(): Response<List<AdResponseBody>>

    @GET("ad/sortByPostedDateAsc")
    suspend fun sortByPostedDateAsc(): Response<List<AdResponseBody>>

    @GET("ad/{id}")
    suspend fun getAdDetails(@Path("id") id: Long): Response<AdResponseBody>

    @POST("/auth/registration")
    suspend fun registration(@Body userRequest: UserRequest)
}