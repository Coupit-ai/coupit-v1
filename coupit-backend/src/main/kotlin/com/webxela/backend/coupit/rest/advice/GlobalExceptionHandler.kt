package com.webxela.backend.coupit.rest.advice

import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.domain.exception.ApiResponse
import org.apache.logging.log4j.LogManager
import org.springframework.core.codec.EncodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val logger = LogManager.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(ApiError::class)
    fun handleApiError(apiError: ApiError): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.error<Nothing>(
            status = apiError.status,
            message = apiError.message,
            exception = apiError.exception
        )
        return ResponseEntity(response, apiError.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "An unexpected error occurred.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(EncodingException::class)
    fun handleJsonEncodingException(ex: EncodingException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "Error while processing json response.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "Error while processing your request.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }


    @ExceptionHandler(MethodNotAllowedException::class)
    fun handleMethodNotAllowedException(ex: MethodNotAllowedException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = "Method not allowed.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = "Method not allowed.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }


    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(ex: ServerWebInputException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = "Malformed or invalid input provided.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.METHOD_NOT_ALLOWED,
            message = "Malformed or invalid input argument provided.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun userNameNotFoundException(ex: UsernameNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.NOT_FOUND,
            message = "Username not found.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun userNameNotFoundException(ex: NoResourceFoundException): ResponseEntity<ApiResponse<Nothing>> {
        logger.error(ex.message, ex)
        val response = ApiResponse.error<Nothing>(
            status = HttpStatus.BAD_REQUEST,
            message = "Static resources could not be found.",
            exception = ex.message
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}