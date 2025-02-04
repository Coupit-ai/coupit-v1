package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.DeviceType
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.data.model.dto.FcmTokenDto
import com.webxela.app.coupit.data.model.dto.FcmTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class FirebaseManager(private val httpClient: HttpClient) {

    suspend fun updateNewToken(
        token: String,
        deviceType: DeviceType,
    ): ApiResponse<FcmTokenDto, DataError.Remote> {
        return safeCall<FcmTokenDto> {
            httpClient.post("${AppConstant.BASE_URL}/fcm/token") {
                contentType(ContentType.Application.Json)
                setBody(FcmTokenRequest(token, deviceType))
            }
        }
    }

}