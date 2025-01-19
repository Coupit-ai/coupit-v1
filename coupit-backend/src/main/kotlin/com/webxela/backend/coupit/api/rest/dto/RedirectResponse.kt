package com.webxela.backend.coupit.api.rest.dto

data class RedirectResponse(
    val redirectUri: String,
    val message: String
)