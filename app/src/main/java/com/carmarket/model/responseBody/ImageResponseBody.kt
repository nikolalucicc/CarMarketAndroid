package com.carmarket.model.responseBody

import com.google.gson.annotations.SerializedName

data class ImageResponseBody(

    @SerializedName("id")
    val id: Long,

    @SerializedName("url")
    val url: String

)
