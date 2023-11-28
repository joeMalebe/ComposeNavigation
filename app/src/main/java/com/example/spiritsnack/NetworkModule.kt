package com.example.spiritsnack

import com.example.composenavigation.BuildConfig
import com.example.spiritsnack.data.service.UncoveredTreasureService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): UncoveredTreasureService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
            .create(UncoveredTreasureService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor) =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(
            httpLoggingInterceptor
        ).build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("x-rapidapi-key", BuildConfig.apiKey)
                .header("x-rapidapi-host", BuildConfig.baseUrl)
            chain.proceed(requestBuilder.build())
        }
    }
}