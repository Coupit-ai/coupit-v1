package com.webxela.app.coupit.core.presentation

import com.webxela.app.coupit.core.domain.DataError


fun DataError.toErrorMessage(): String {
    val message = when (this) {
        is DataError.Remote.NoInternet -> this.message ?: "Please connect with internet"
        is DataError.Remote.NotFound -> this.message ?: "Resource not found"
        is DataError.Remote.RequestTimeout -> this.message ?: "Request timeout"
        is DataError.Remote.SerializationError -> this.message ?: "Something went wrong, Try again"
        is DataError.Remote.ServerError -> this.message ?: "Something went wrong"
        is DataError.Remote.TooManyRequests -> this.message ?: "Too many requests, Slow down"
        is DataError.Remote.UnknownError -> this.message ?: "Unknown error"
        is DataError.Remote.Unauthorised -> this.message ?: "Please connect your square account"
    }

    return message
}