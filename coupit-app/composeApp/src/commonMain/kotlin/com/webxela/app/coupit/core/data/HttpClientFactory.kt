package com.webxela.app.coupit.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal object HttpClientFactory {

    internal fun create(
        engine: HttpClientEngine,
        jwtTokenProvider: () -> String?
    ): HttpClient {
        return HttpClient(engine) {
            followRedirects = true
            install(DefaultRequest) {
                jwtTokenProvider()?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }
//            engine {
//                https {
//                    serverName = "api.coupit.ai"
//                }
//            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 10_000L
                requestTimeoutMillis = 10_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }
    }
}