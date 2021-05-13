package com.rajat.flow.data

import com.rajat.flow.domain.Brewery

class BreweryRepository(private val breweryDataSource: BreweryDataSource) {

    suspend fun getBreweries(query: String): List<Brewery> = breweryDataSource.getBreweries()

}