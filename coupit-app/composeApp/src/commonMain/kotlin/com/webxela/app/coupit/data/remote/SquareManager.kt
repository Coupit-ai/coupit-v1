package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.utils.AppConstant.BASE_URL
import com.webxela.app.coupit.data.model.dto.ConnectionDto
import com.webxela.app.coupit.data.model.dto.LogOutDto
import com.webxela.app.coupit.data.model.dto.MerchantDto
import com.webxela.app.coupit.domain.model.Merchant
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SquareManager(private val httpClient: HttpClient) {

    suspend fun connectWithSquare(state: String): ApiResponse<ConnectionDto, DataError.Remote> {
        return safeCall<ConnectionDto> {
            httpClient.get("$BASE_URL/square/oauth/connect/$state") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getLoggedInUser(): ApiResponse<MerchantDto, DataError.Remote> {
        return safeCall<MerchantDto> {
            httpClient.get("$BASE_URL/square/me") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun logout(): ApiResponse<LogOutDto, DataError.Remote> {
        return safeCall<LogOutDto> {
            httpClient.get("$BASE_URL/square/oauth/revoke") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}