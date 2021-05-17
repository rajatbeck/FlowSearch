package com.jetpack.data.remote

import android.util.Log
import com.jetpack.data.Resource
import com.jetpack.data.dto.Breweries
import com.jetpack.data.error.DEFAULT_ERROR
import com.jetpack.data.error.NETWORK_ERROR
import com.jetpack.data.error.NO_INTERNET_CONNECTION
import com.jetpack.data.remote.service.BreweryService
import com.jetpack.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val service: BreweryService,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {

    override suspend fun searchBreweries(query: String): Resource<List<Breweries>> {
        Log.d("Remote data ","QUery is $query")
        return when(val response = processCall { service.searchBreweries(query) } ){
            is List<*> -> {
                Resource.Success(data = (response as? List<Breweries>).orEmpty())
            }
            else -> {
                Resource.DataError(RuntimeException())
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}