package com.jetpack.di

import com.rajat.data.SearchRepositoryImpl
import com.rajat.data.error.ErrorHandler
import com.rajat.data.mapper.SearchResponseMapper
import com.rajat.data.remote.BreweryService
import com.rajat.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesRepositoryImpl(
        api: BreweryService,
        errorHandler: ErrorHandler,
        mapper: SearchResponseMapper
    ): SearchRepository {
        return SearchRepositoryImpl(api, errorHandler, mapper)
    }
}