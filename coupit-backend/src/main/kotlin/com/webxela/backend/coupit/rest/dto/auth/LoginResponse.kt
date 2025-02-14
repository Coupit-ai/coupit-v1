package com.webxela.backend.coupit.rest.dto.auth

data class LoginResponse(
    val email: String,
    val token: String
)
