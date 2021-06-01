package com.rajat.data.remote

import com.rajat.data.Brewery
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryService {
    @GET("breweries/autocomplete")
    suspend fun searchBreweries(@Query("query") query: String): List<Brewery>
}