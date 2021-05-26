package com.rajat.domain.usecase

import com.rajat.domain.model.Breweries
import com.rajat.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchUseCase constructor(
    private val dispatcher: Dispatchers,
    private val repository: SearchRepository
) {

    suspend fun performSearch(query: String): List<Breweries> {
        return withContext(dispatcher.IO) {
            repository.getBreweries(query)
        }
    }
}