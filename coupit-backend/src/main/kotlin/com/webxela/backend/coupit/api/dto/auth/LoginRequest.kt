package com.webxela.backend.coupit.api.dto.auth

data class LoginRequest(
    val email: String,
    val password: String
)
