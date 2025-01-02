package com.webxela.backend.coupit.common.exception

import org.springframework.http.HttpStatus

sealed class ApiError(
    val status: HttpStatus,
    val exception: String? = null,
    override val message: String? = null
) : RuntimeException(exception) {

    class ResourceNotFound(
        exception: Exception? = null,
        message: String? = null
    ) : ApiError(
        status = HttpStatus.NOT_FOUND,
        message = message,
        exception = exception?.message
    )

    class BadRequest(
        exception: Exception? = null,
        message: String? = null
    ) : ApiError(
        status = HttpStatus.BAD_REQUEST,
        message = message,
        exception = exception?.message
    )

    class Unauthorized(
        exception: Exception? = null,
        message: String? = null,
    ) : ApiError(
        status = HttpStatus.UNAUTHORIZED,
        message = message,
        exception = exception?.message
    )

    class TooManyRequests(
        exception: Exception? = null,
        message: String? = null,
    ) : ApiError(
        status = HttpStatus.TOO_MANY_REQUESTS,
        message = message,
        exception = exception?.message
    )

    class InternalError(
        exception: Exception? = null,
        message: String? = null,
    ) : ApiError(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        message = message ?: "An unexpected exception occurred",
        exception = exception?.message
    )

    class DataIntegrityError(
        exception: Exception? = null,
        message: String? = null,
    ) : ApiError(
        status = HttpStatus.BAD_REQUEST,
        message = message,
        exception = exception?.message
    )
}