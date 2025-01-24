package com.webxela.backend.coupit.api.dto.auth

data class RedirectResponse(
    val redirectUri: String,
    val message: String
)