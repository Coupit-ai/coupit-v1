package com.webxela.app.coupit.core.domain

sealed interface DataError : Error {
    sealed interface Remote : DataError {
        data class RequestTimeout(val message: String?) : Remote
        data class TooManyRequests(val message: String?) : Remote
        data class NoInternet(val message: String?) : Remote
        data class Unauthorised(val message: String?) : Remote
        data class ServerError(val message: String?) : Remote
        data class SerializationError(val message: String?) : Remote
        data class NotFound(val message: String?) : Remote
        data class UnknownError(val message: String? = null) : Remote
    }
}