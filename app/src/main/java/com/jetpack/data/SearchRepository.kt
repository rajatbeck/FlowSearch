package com.jetpack.data

import com.jetpack.data.dto.Breweries
import com.jetpack.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchRepository @Inject constructor(
    private val remoteRepo: RemoteData,
    private val ioDispatcher: CoroutineContext
) : SearchRepositorySource {
    override suspend fun searchBreweries(searchQuery: String): Flow<Resource<List<Breweries>>> {
        return flow {
            emit(remoteRepo.searchBreweries(searchQuery))
        }.flowOn(ioDispatcher)

    }
}