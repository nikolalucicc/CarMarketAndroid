package com.carmarket.model.dto

data class UserDTO(

    val id: Long,
    val email: String,
    val password: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val registrationDate: List<Int>

)
