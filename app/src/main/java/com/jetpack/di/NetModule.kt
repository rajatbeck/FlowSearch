package com.jetpack.di

import com.jetpack.BuildConfig
import com.jetpack.data.remote.moshi_factories.MyKotlinJsonAdapter
import com.jetpack.data.remote.moshi_factories.MyKotlinJsonAdapterFactory
import com.jetpack.data.remote.moshi_factories.MyStandardJsonAdapters
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api.openbrewerydb.org"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun providesOkHttp():OkHttpClient{
        return if(BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                )
                .build()
        }else{
            OkHttpClient.Builder().build()
        }
    }

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder()
        .add(MyKotlinJsonAdapterFactory())
        .add(MyStandardJsonAdapters.FACTORY)
        .build()

    @Singleton
    @Provides
    fun providesRetrofitInstance(okHttpClient: OkHttpClient,moshi: Moshi):Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
}