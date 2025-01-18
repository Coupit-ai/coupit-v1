package com.webxela.backend.coupit.api.rest.dto.auth

import java.time.Instant

data class SignupResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: Instant,
)