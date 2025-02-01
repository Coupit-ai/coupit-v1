package com.webxela.app.coupit.core.presentation

import com.webxela.app.coupit.core.domain.DataError


fun DataError.toErrorMessage(): String {
    val message = when (this) {
        is DataError.Remote.NoInternet -> this.message
        is DataError.Remote.NotFound -> this.message
        is DataError.Remote.RequestTimeout -> this.message
        is DataError.Remote.SerializationError -> this.message
        is DataError.Remote.ServerError -> this.message
        is DataError.Remote.TooManyRequests -> this.message
        is DataError.Remote.UnknownError -> this.message
        is DataError.Remote.Unauthorised -> this.message
    } ?: "Unknown error occurred."

    return message
}