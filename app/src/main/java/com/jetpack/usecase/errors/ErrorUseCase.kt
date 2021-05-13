package com.jetpack.usecase.errors

import com.jetpack.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}