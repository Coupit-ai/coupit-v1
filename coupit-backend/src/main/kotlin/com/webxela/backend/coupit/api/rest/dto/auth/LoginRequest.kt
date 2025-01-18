package com.webxela.backend.coupit.api.rest.dto.auth

data class LoginRequest(
    val email: String,
    val password: String
)
