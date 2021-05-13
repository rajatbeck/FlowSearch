package com.jetpack.data.remote

import com.jetpack.data.Resource
import com.jetpack.data.dto.Breweries

internal interface RemoteDataSource {
    suspend fun searchBreweries(query: String):Resource<List<Breweries>>
}