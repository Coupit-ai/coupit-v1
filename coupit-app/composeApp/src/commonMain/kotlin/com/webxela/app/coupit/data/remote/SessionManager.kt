package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.utils.AppConstant.BASE_URL
import com.webxela.app.coupit.data.model.dto.SessionDto
import com.webxela.app.coupit.data.model.dto.SessionRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay

class SessionManager(private val httpClient: HttpClient) {

    suspend fun createSession(
        merchantId: String,
        transactionId: String
    ): ApiResponse<SessionDto, DataError.Remote> {

        return safeCall<SessionDto> {
            delay(3000)
            httpClient.post("$BASE_URL/session") {
                contentType(ContentType.Application.Json)
                setBody(
                    SessionRequest(
                        merchantId = merchantId,
                        transactionId = transactionId
                    )
                )
            }
        }
    }

    suspend fun getSession(
        sessionId: String
    ): ApiResponse<SessionDto, DataError.Remote> {

        return safeCall<SessionDto> {
            httpClient.get("$BASE_URL/$sessionId") {
                contentType(ContentType.Application.Json)
            }
        }
    }

}