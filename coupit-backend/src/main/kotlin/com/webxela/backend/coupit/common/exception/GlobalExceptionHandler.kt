package com.webxela.backend.coupit.common.exception

import org.apache.logging.log4j.LogManager
import org.springframework.core.codec.EncodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.MethodNotAllowedException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LogManager.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ApiError::class)
    fun handleApiError(apiError: ApiError): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.error<Nothing>(
            status = apiError.status,
            message = apiError.message,
            exception = apiError.exception
        )
        return ResponseEntity(response, apiError.status)
    }

    @ExceptionHandler(EncodingException::class)
    fun handleJsonEncodingException(ex: EncodingException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "Error while parsing json",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodNotAllowedException::class)
    fun handleMethodNotAllowedException(ex: MethodNotAllowedException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = "Method not allowed",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleResourceNotFoundException(ex: NoResourceFoundException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.NOT_FOUND,
            message = "No such resource found",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "An unexpected error occurred",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}