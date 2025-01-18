package com.webxela.backend.coupit.common.exception

import org.springframework.http.HttpStatus

sealed class ApiError(
    val status: HttpStatus,
    val exception: String? = null,
    override val message: String
) : RuntimeException(exception) {

    class ResourceNotFound(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.NOT_FOUND,
        message = message,
        exception = exception?.message
    )

    class BadRequest(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.BAD_REQUEST,
        message = message,
        exception = exception?.message
    )

    class Unauthorized(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.UNAUTHORIZED,
        message = message,
        exception = exception?.message
    )

    class TooManyRequests(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.TOO_MANY_REQUESTS,
        message = message,
        exception = exception?.message
    )

    class InternalError(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        message = message,
        exception = exception?.message
    )

    class DataIntegrityError(
        message: String,
        exception: Exception? = null
    ) : ApiError(
        status = HttpStatus.BAD_REQUEST,
        message = message,
        exception = exception?.message
    )
}