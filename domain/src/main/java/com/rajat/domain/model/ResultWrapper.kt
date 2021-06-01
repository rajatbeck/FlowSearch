package com.rajat.domain.model

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val errorEntity: ErrorEntity) : ResultWrapper<Nothing>()
}