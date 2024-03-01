package com.carmarket.model.dto

data class AdDTO(

    val id: Long,
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
    val postedDate: List<Int>,
    val images: List<ImageDTO>

)
