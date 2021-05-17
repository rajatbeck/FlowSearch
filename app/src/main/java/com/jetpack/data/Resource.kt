package com.jetpack.data

sealed class Resource<out T:Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class DataError(val throwable: Throwable) : Resource<Nothing>()
}