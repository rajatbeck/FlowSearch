package com.rajat.data

import com.rajat.data.error.ErrorHandler
import com.rajat.data.mapper.SearchResponseMapper
import com.rajat.data.remote.BreweryService
import com.rajat.domain.model.Breweries
import com.rajat.domain.model.ResultWrapper
import com.rajat.domain.repository.SearchRepository
import java.lang.Exception

class SearchRepositoryImpl constructor(
    private val api: BreweryService,
    private val errorHandler: ErrorHandler,
    private val mapper: SearchResponseMapper
) : SearchRepository {

    override suspend fun getBreweries(query: String): ResultWrapper<List<Breweries>> {
        return try {
            api.searchBreweries(query)
                .map { mapper.map(it) }
                .let { ResultWrapper.Success(it) }
        } catch (e: Exception) {
            ResultWrapper.Error(errorHandler.getError(e))
        }

    }
}

