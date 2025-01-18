package com.webxela.backend.coupit.api.rest.dto.auth

data class LoginResponse(
    val email: String,
    val token: String
)
