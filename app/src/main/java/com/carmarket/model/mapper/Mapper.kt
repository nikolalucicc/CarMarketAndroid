package com.carmarket.model.mapper

interface Mapper<F,T> {
    fun mapTo(responseBody: F): T
}

