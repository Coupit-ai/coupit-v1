package com.webxela.app.coupit.core.data

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.Error
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext

@Serializable
data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val timeStamp: String,
    val error: String? = null
)

internal suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): ApiResponse<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (ex: SocketTimeoutException) {
        return ApiResponse.Error(DataError.Remote.RequestTimeout("Request timed out."))
    } catch (ex: UnresolvedAddressException) {
        return ApiResponse.Error(DataError.Remote.NoInternet("No internet connection."))
    } catch (ex: Exception) {
        coroutineContext.ensureActive()
        return ApiResponse.Error(DataError.Remote.UnknownError(ex.message ?: "Unknown Error"))
    }

    return responseToResult(response)
}

private suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): ApiResponse<T, DataError.Remote> {
    return if (response.status.isSuccess()) {
        try {
            ApiResponse.Success(response.body<T>())
        } catch (e: NoTransformationFoundException) {
            ApiResponse.Error(DataError.Remote.SerializationError("Failed to deserialize response."))
        }
    } else {
        parseErrorResponse(response)
    }
}

private suspend fun parseErrorResponse(response: HttpResponse): ApiResponse.Error<DataError.Remote> {
    return try {
        val errorBody = response.bodyAsText()
        val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
        when (response.status.value) {
            408 -> ApiResponse.Error(DataError.Remote.RequestTimeout(errorResponse.message))
            429 -> ApiResponse.Error(DataError.Remote.TooManyRequests(errorResponse.message))
            404 -> ApiResponse.Error(DataError.Remote.NotFound(errorResponse.message))
            in 500..599 -> ApiResponse.Error(DataError.Remote.ServerError(errorResponse.message))
            else -> ApiResponse.Error(DataError.Remote.UnknownError(errorResponse.message))
        }
    } catch (e: Exception) {
        ApiResponse.Error(DataError.Remote.UnknownError("Failed to parse error response"))
    }
}

internal inline fun <T, R, E : Error> ApiResponse<T, E>.mapSuccess(transform: (T) -> R): ApiResponse<R, E> {
    return when (this) {
        is ApiResponse.Success -> ApiResponse.Success(transform(this.data))
        is ApiResponse.Error -> ApiResponse.Error(this.error)
    }
}