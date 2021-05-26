package com.rajat.domain.repository

import com.rajat.domain.model.Breweries

interface SearchRepository {
    suspend fun getBreweries(query: String): List<Breweries>
}