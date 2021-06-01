package com.rajat.domain.usecase

import com.rajat.domain.model.Breweries
import com.rajat.domain.model.ResultWrapper
import com.rajat.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend fun performSearch(query: String): ResultWrapper<List<Breweries>> {
        return repository.getBreweries(query)
    }
}