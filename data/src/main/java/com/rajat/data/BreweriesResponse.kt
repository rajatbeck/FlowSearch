package com.rajat.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Brewery(
    @Json(name="id")val id:String?= null,
    @Json(name = "name") val name: String? = null
)