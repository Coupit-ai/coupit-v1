package com.webxela.backend.coupit.domain.exception

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val statusCode: Int,
    val message: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val timeStamp: LocalDateTime = LocalDateTime.now(),
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


