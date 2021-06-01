package com.jetpack.di

import com.rajat.data.error.ErrorHandler
import com.rajat.data.error.ErrorHandlerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    @Singleton
    abstract fun bindsErrorImp(errorHandlerImp: ErrorHandlerImp):ErrorHandler
}