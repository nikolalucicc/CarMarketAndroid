package com.carmarket.model.dto

data class FavoriteDTO (
    val id: Long,
    val user: UserDTO,
    val ad: AdDTO
)