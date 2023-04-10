package com.example.sample.data.remote.rest.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.example.sample.BuildConfig
import com.example.sample.data.remote.rest.dto.reddit.common.module
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        serializersModule = module
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @RetrofitQualifier(RetrofitType.REDDIT)
    fun provideRedditOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun provideImageLoader(
        okHttpCallFactory: Call.Factory,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
            .callFactory(okHttpCallFactory)
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    @RetrofitQualifier(RetrofitType.REDDIT)
    fun provideRetrofitReddit(
        @RetrofitQualifier(RetrofitType.REDDIT)
        okHttpCallFactory: Call.Factory,
        json: Json
    ): Retrofit {

        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(RetrofitType.REDDIT.api)
            .addConverterFactory(json.asConverterFactory(contentType))
            .callFactory(okHttpCallFactory)
            .build()
    }
}