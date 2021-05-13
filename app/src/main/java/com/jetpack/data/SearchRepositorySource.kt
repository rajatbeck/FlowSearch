package com.jetpack.data

import com.jetpack.data.dto.Breweries
import kotlinx.coroutines.flow.Flow

interface SearchRepositorySource {
    suspend fun searchBreweries(searchQuery: String): Flow<Resource<List<Breweries>>>
}