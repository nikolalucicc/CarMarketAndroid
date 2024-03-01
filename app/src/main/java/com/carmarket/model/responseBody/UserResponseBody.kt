package com.carmarket.model.responseBody

import com.google.gson.annotations.SerializedName

data class UserResponseBody (

    @SerializedName("id")
    val id: Long,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("registrationDate")
    val registrationDate: List<Int>
)