package com.webxela.backend.coupit.domain.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val statusCode: Int,
    val message: String? = null,
    val timeStamp: Instant = Instant.now(),
    val data: T? = null,
    val error: String? = null
) {
    companion object {
        private const val DEFAULT_SUCCESS_MESSAGE = "Resource fetched successfully"

        fun <T> success(
            data: T,
            message: String = DEFAULT_SUCCESS_MESSAGE
        ) = ApiResponse(
            statusCode = HttpStatus.OK.value(),
            message = message,
            data = data
        )

        fun <T> error(
            status: HttpStatus,
            message: String? = null,
            exception: String?
        ) = ApiResponse<T>(
            statusCode = status.value(),
            message = message,
            error = exception
        )
    }
}


