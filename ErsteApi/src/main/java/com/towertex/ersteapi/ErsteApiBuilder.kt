package com.towertex.ersteapi

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ErsteApiBuilder(buildBlock: ErsteApiBuilder.() -> Unit) {
    private var baseUrl: String = BASE_URL
    private var apiKey: String = API_KEY
    private var logLevel: LogLevel = LogLevel.HEADERS
    private var logLogger: Logger? = null
    private var pageSize: Int = PAGE_SIZE

    init {
        buildBlock.invoke(this)
    }

    companion object {
        private const val BASE_URL = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v3/"
        private const val API_KEY = "e3bb1b79-6cc8-40aa-a27f-f02663d4791f"
        private const val PAGE_SIZE = 25
        private const val HEADER_AUTH_KEY = "WEB-API-key"
    }

    fun setBaseUrl(baseUrl: String) = this.apply { this.baseUrl = baseUrl }
    fun setApiKey(apiKey: String) = this.apply { this.apiKey = apiKey }
    fun setLogAll() = this.apply { logLevel = LogLevel.ALL }
    fun setLogBody() = this.apply { logLevel = LogLevel.BODY }
    fun setLogHeaders() = this.apply { logLevel = LogLevel.HEADERS }
    fun setLogInfo() = this.apply { logLevel = LogLevel.INFO }
    fun setLogNone() = this.apply { logLevel = LogLevel.NONE }
    fun setDebugLogger() = this.apply { logLogger = object : Logger {
        override fun log(message: String) {
            println(message)
        }
    } }
    fun setReleaseLogger() = this.apply { logLogger = null }
    fun setPageSize(pageSize: Int) = this.apply { this.pageSize = pageSize }

    fun build(): ErsteApi = ErsteApi(getClient(), HEADER_AUTH_KEY to apiKey, baseUrl, pageSize)

    private fun getClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            level = logLevel
            logLogger?.also { logger = it }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
    }

}