package com.carmarket.model.responseBody

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(

    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("expiresIn")
    val expiresIn: Long

)
