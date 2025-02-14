package com.webxela.backend.coupit.rest.dto.auth

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SignupResponse(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val createdAt: Instant,
    val token: String
)