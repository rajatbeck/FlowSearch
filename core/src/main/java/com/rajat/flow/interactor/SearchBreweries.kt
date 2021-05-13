package com.rajat.flow.interactor

import com.rajat.flow.data.BreweryRepository

class SearchBreweries(private val breweryRepository: BreweryRepository) {
    suspend fun invoke(searchQuery: String) = breweryRepository.getBreweries(searchQuery)
}