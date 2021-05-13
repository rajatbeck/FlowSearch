package com.jetpack.data.dto

import com.squareup.moshi.Json

data class Breweries(
    @Json(name="id") val id: String? = null,
    @Json(name = "name") val name: String? = null
)