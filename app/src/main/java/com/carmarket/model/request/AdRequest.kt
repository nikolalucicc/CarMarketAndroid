package com.carmarket.model.request

data class AdRequest (

    val description: String,
    val brand: String,
    val carBody: String,
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
    val drive: String,
    val doorNumber: String,
    val seatsNumber: String,
    val wheelSide: String,
    val airConditioning: String,
    val color: String,
    val interiorColor: String,
    val registeredUntil: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val imageUrls: List<String>

)