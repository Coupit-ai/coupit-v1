package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.utils.AppConstant.BASE_URL
import com.webxela.app.coupit.data.model.dto.SessionDto
import com.webxela.app.coupit.data.model.dto.SessionRequest
import com.webxela.app.coupit.data.model.dto.SpinRequest
import com.webxela.app.coupit.data.model.dto.SpinResultDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SpinManager(private val httpClient: HttpClient) {

    suspend fun performSpin(
        merchantId: String,
        sessionId: String
    ): ApiResponse<SpinResultDto, DataError.Remote> {

        return safeCall<SpinResultDto> {
            httpClient.post("$BASE_URL/spin") {
                contentType(ContentType.Application.Json)
                setBody(
                    SpinRequest(
                        merchantId = merchantId,
                        sessionId = sessionId
                    )
                )
            }
        }
    }

    suspend fun getSpinResult(spinId: String): ApiResponse<SpinResultDto, DataError.Remote> {

        return safeCall<SpinResultDto> {
            httpClient.get("$BASE_URL/spin/$spinId") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}