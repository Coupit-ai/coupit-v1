package com.webxela.backend.coupit.api.dto.auth

data class LoginResponse(
    val email: String,
    val token: String
)
