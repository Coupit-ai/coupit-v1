package com.webxela.backend.coupit.rest.dto.auth

data class SignupRequest(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val password: String
)