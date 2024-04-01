package com.carmarket.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //Wifi
//    private const val BASE_URL = "http://192.168.1.9:8080/"

    //Telefon
    private const val BASE_URL = "http://172.20.10.2:8080/"

    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}