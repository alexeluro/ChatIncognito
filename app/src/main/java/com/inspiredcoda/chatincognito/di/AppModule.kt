package com.inspiredcoda.chatincognito.di

import com.inspiredcoda.chatincognito.data.HttpService
import com.inspiredcoda.chatincognito.data.HttpServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.util.pipeline.PipelineInterceptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                connectTimeoutMillis = 60000
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    @Singleton
    @Provides
    fun providesHttpService(httpClient: HttpClient, json: Json): HttpService {
        return HttpServiceImpl(httpClient, json)
    }

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
        }
    }

}