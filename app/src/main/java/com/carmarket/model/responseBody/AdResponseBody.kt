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

    @SerializedName("year")
    val year: Long,

    @SerializedName("price")
    val price: Long,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("mileage")
    val mileage: Long,

    @SerializedName("engineType")
    val engineType: String,

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
    val images: List<ImageResponseBody>

)
