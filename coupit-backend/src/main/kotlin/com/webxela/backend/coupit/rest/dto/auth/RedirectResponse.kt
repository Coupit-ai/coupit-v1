package com.webxela.backend.coupit.rest.dto.auth

data class RedirectResponse(
    val redirectUri: String,
    val message: String
)