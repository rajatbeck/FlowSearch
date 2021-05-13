package com.rajat.flow.data

import com.rajat.flow.domain.Brewery

interface BreweryDataSource {
    suspend fun getBreweries(): List<Brewery>
}