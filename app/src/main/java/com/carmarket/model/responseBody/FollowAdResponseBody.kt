package com.carmarket.model.responseBody

import com.google.gson.annotations.SerializedName

data class FollowAdResponseBody(

    @SerializedName("id")
    val id: Long,

    @SerializedName("user")
    val user: UserResponseBody,

    @SerializedName("ad")
    val ad: AdResponseBody
)
