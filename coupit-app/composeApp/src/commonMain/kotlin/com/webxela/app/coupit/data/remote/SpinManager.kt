package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.utils.AppConstant.BASE_URL
import com.webxela.app.coupit.data.model.dto.SpinConfigDto
import com.webxela.app.coupit.data.model.dto.SpinRequest
import com.webxela.app.coupit.data.model.dto.SpinResultDto
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SpinManager(private val httpClient: HttpClient) {

    suspend fun performSpin(sessionId: String): ApiResponse<SpinResultDto, DataError.Remote> {
        return safeCall<SpinResultDto> {
            httpClient.post("$BASE_URL/spin") {
                contentType(ContentType.Application.Json)
                setBody(SpinRequest(sessionId))
            }
        }
    }

    suspend fun getSpinConfig(sessionId: String): ApiResponse<SpinConfigDto, DataError.Remote> {
        return safeCall<SpinConfigDto> {
            httpClient.get("$BASE_URL/spin/config/$sessionId") {
                contentType(ContentType.Application.Json)
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