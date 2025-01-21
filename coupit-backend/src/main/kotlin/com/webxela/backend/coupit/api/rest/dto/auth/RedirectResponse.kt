package com.webxela.backend.coupit.api.rest.dto.auth

data class RedirectResponse(
    val redirectUri: String,
    val message: String
)