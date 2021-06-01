package com.rajat.data.error

import com.rajat.domain.model.ErrorEntity
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorHandlerImp @Inject constructor():ErrorHandler{

    override fun getError(throwable: Throwable): ErrorEntity {
       return when(throwable){
           is IOException -> ErrorEntity.Network
           is HttpException -> {
               when(throwable.code()){
                   HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                   HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
                   HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                   else -> ErrorEntity.Unknown
               }
           }
           else -> ErrorEntity.Unknown
       }
    }
}