package com.carmarket.repository

import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.network.CarMarketApi
import java.net.URLEncoder

class SearchAdRepository(
    private val api: CarMarketApi
) {
    suspend fun searchAd(
        brand: String?,
        model: String?,
        carBody: String?,
        minYear: Int?,
        maxYear: Int?,
        minPrice: Int?,
        maxPrice: Int?,
        condition: String?,
        minMileage: Int?,
        maxMileage: Int?,
        fuel: String?,
        minEngineHp: Int?,
        maxEngineHp: Int?,
        minEngineKw: Int?,
        maxEngineKw: Int?,
        minEngineCubic: Int?,
        maxEngineCubic: Int?,
        transmission: String?,
        city: String?,
        country: String?,
        drive: String?,
        doorNumber: String?,
        seatsNumber: String?,
        wheelSide: String?,
        airConditioning: String?,
        color: String?,
        interiorColor: String?,
        registeredUntil: String?,
        phoneNumber: String?,
        bearerToken: String
    ): List<AdResponseBody> {

        val response = api.searchAd(
            brand = brand.takeIf { !it.isNullOrEmpty() },
            model = model.takeIf { !it.isNullOrEmpty() },
            carBody = carBody.takeIf { !it.isNullOrEmpty() },
            minYear = minYear,
            maxYear = maxYear,
            minPrice = minPrice,
            maxPrice = maxPrice,
            condition = condition.takeIf { !it.isNullOrEmpty() },
            minMileage = minMileage,
            maxMileage = maxMileage,
            fuel = fuel.takeIf { !it.isNullOrEmpty() },
            minEngineHp = minEngineHp,
            maxEngineHp = maxEngineHp,
            minEngineKw = minEngineKw,
            maxEngineKw = maxEngineKw,
            minEngineCubic = minEngineCubic,
            maxEngineCubic = maxEngineCubic,
            transmission = transmission.takeIf { !it.isNullOrEmpty() },
            city = city.takeIf { !it.isNullOrEmpty() },
            country = country.takeIf { !it.isNullOrEmpty() },
            drive = drive.takeIf { !it.isNullOrEmpty() },
            doorNumber = doorNumber.takeIf { !it.isNullOrEmpty() },
            seatsNumber = seatsNumber.takeIf { !it.isNullOrEmpty() },
            wheelSide = wheelSide.takeIf { !it.isNullOrEmpty() },
            airConditioning = airConditioning.takeIf { !it.isNullOrEmpty() },
            color = color.takeIf { !it.isNullOrEmpty() },
            interiorColor = interiorColor.takeIf { !it.isNullOrEmpty() },
            registeredUntil = registeredUntil.takeIf { !it.isNullOrEmpty() },
            phoneNumber = phoneNumber.takeIf { !it.isNullOrEmpty() },
            bearerToken
        )

        return response.body() ?: throw IllegalStateException("The response body is null.")
    }
}