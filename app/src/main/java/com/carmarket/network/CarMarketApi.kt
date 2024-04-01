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
import retrofit2.http.Query

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

    @DELETE("/favorite/delete/{userId}/{adId}")
    suspend fun deleteFromFavorites(@Path("userId") userId: Long, @Path("adId") adId: Long, @Header("Authorization") bearerToken: String)

    @PUT("/ad/update/{id}")
    suspend fun changeAd(@Body adRequest: AdRequest, @Path("id") id: Long, @Header("Authorization") bearerToken: String)

    @DELETE("/ad/delete/{id}")
    suspend fun removeAd(@Path("id") id: Long, @Header("Authorization") bearerToken: String)

    @GET("/ad/search")
    suspend fun searchAd(
        @Query("brand") brand: String? = null,
        @Query("model") model: String? = null,
        @Query("carBody") carBody: String? = null,
        @Query("minYear") minYear: Int? = null,
        @Query("maxYear") maxYear: Int? = null,
        @Query("minPrice") minPrice: Int? = null,
        @Query("maxPrice") maxPrice: Int? = null,
        @Query("condition") condition: String? = null,
        @Query("minMileage") minMileage: Int? = null,
        @Query("maxMileage") maxMileage: Int? = null,
        @Query("fuel") fuel: String? = null,
        @Query("minEngineHp") minEngineHp: Int? = null,
        @Query("maxEngineHp") maxEngineHp: Int? = null,
        @Query("minEngineKw") minEngineKw: Int? = null,
        @Query("maxEngineKw") maxEngineKw: Int? = null,
        @Query("minEngineCubic") minEngineCubic: Int? = null,
        @Query("maxEngineCubic") maxEngineCubic: Int? = null,
        @Query("transmission") transmission: String? = null,
        @Query("city") city: String? = null,
        @Query("country") country: String? = null,
        @Query("drive") drive: String? = null,
        @Query("doorNumber") doorNumber: String? = null,
        @Query("seatsNumber") seatsNumber: String? = null,
        @Query("wheelSide") wheelSide: String? = null,
        @Query("airConditioning") airConditioning: String? = null,
        @Query("color") color: String? = null,
        @Query("interiorColor") interiorColor: String? = null,
        @Query("registeredUntil") registeredUntil: String? = null,
        @Query("phoneNumber") phoneNumber: String? = null,
        @Header("Authorization") bearerToken: String
    ): Response<List<AdResponseBody>>

}