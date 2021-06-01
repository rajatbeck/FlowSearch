package com.rajat.data.error

import com.rajat.domain.model.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}