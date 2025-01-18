package com.webxela.backend.coupit.api.rest.dto.auth

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)