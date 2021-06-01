package com.rajat.domain.model

sealed class ErrorEntity(val code: Int? = -1, val message: String? = "") {

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}