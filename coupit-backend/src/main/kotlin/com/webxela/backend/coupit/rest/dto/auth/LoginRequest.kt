package com.webxela.backend.coupit.rest.dto.auth

data class LoginRequest(
    val email: String,
    val password: String
)
