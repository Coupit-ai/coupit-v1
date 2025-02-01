package com.webxela.app.coupit.core.presentation.navigation

import androidx.compose.runtime.compositionLocalOf

class ErrorHandler(val showError: (String) -> Unit)

val LocalErrorHandler = compositionLocalOf<ErrorHandler> {
    error("No error handler provided")
}