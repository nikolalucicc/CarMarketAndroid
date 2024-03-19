package com.carmarket.network

import com.carmarket.model.request.AdRequest
import com.carmarket.model.request.FollowAdRequest
import com.carmarket.model.request.LoginRequest
import com.carmarket.model.request.UserRequest
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.model.responseBody.FollowAdResponseBody
import com.carmarket.model.responseBody.LoginResponseBody
import com.carmarket.model.responseBody.UserResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponseBody>

    @POST("/ad/createAd")
    suspend fun createAd(@Body adRequest: AdRequest, @Header("Authorization") bearerToken: String): Response<AdResponseBody>

    @GET("/ad/adsByUser/{id}")
    suspend fun getAdsByUser(@Path("id") id: Long, @Header("Authorization") bearerToken: String) : Response<List<AdResponseBody>>

    @GET("/auth/{username}")
    suspend fun getUserDetails(@Path("username") username: String) : Response<UserResponseBody>

    @PUT("/auth/update/{username}")
    suspend fun updateUser(@Body userRequest: UserRequest, @Path("username") username: String, @Header("Authorization") bearerToken: String)

    @POST("/favorite/save")
    suspend fun followAd(@Body followAdRequest: FollowAdRequest, @Header("Authorization") bearerToken: String) : Response<FollowAdResponseBody>

    @GET("/favorite/favoriteByUser/{id}")
    suspend fun getFollowingAds(@Path("id") id: Long, @Header("Authorization") bearerToken: String) : Response<List<FollowAdResponseBody>>

    @DELETE("/favorite/delete/{id}")
    suspend fun deleteFromFavorites(@Path("id") id: Long, @Header("Authorization") bearerToken: String)
}