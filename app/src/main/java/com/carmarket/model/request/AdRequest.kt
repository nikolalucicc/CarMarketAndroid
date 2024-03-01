package com.carmarket.model.request

import com.carmarket.model.dto.ImageDTO

data class AdRequest (

    val description: String,
    val brand: String,
    val model: String,
    val year: Long,
    val price: Long,
    val condition: String,
    val mileage: Long,
    val engineType: String,
    val engineHp: Long,
    val engineKw: Long,
    val engineCubic: Long,
    val transmission: String,
    val city: String,
    val country: String,
    val images: List<String>

)