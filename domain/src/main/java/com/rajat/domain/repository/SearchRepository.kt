package com.rajat.domain.repository

import com.rajat.domain.model.Breweries
import com.rajat.domain.model.ResultWrapper

interface SearchRepository {
    suspend fun getBreweries(query: String): ResultWrapper<List<Breweries>>
}