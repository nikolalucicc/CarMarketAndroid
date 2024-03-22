package com.carmarket.model.responseBody

import com.google.gson.annotations.SerializedName

data class AdResponseBody(

    @SerializedName("id")
    val id: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("carBody")
    val carBody: String,

    @SerializedName("year")
    val year: Long,

    @SerializedName("price")
    val price: Long,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("mileage")
    val mileage: Long,

    @SerializedName("fuel")
    val fuel: String,

    @SerializedName("engineHp")
    val engineHp: Long,

    @SerializedName("engineKw")
    val engineKw: Long,

    @SerializedName("engineCubic")
    val engineCubic: Long,

    @SerializedName("transmission")
    val transmission: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("postedDate")
    val postedDate: List<Int>,

    @SerializedName("images")
    val images: List<ImageResponseBody>,

    @SerializedName("drive")
    val drive: String,

    @SerializedName("doorNumber")
    val doorNumber: String,

    @SerializedName("seatsNumber")
    val seatsNumber: String,

    @SerializedName("wheelSide")
    val wheelSide: String,

    @SerializedName("airConditioning")
    val airConditioning: String,

    @SerializedName("color")
    val color: String,

    @SerializedName("interiorColor")
    val interiorColor: String,

    @SerializedName("registeredUntil")
    val registeredUntil: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String

)
