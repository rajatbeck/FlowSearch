package com.jetpack.data.remote.service

import com.jetpack.data.dto.Breweries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryService {
    @GET("breweries/autocomplete")
    suspend fun searchBreweries(@Query("query") query: String): Response<List<Breweries>>
}