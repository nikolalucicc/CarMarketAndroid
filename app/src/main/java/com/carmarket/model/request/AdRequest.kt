package com.carmarket.model.request

data class AdRequest (

    val description: String,
    val brand: String,
    val model: String,
    val year: Long,
    val price: Long,
    val condition: String,
    val mileage: Long,
    val fuel: String,
    val engineHp: Long,
    val engineKw: Long,
    val engineCubic: Long,
    val transmission: String,
    val city: String,
    val country: String,
    val images: List<String>

)