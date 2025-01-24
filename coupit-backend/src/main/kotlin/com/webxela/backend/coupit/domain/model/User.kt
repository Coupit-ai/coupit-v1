package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String?,
    val email: String,
    val password: String,
    val createdAt: Instant?,
    val jwtToken: String?,
    val oauthToken: OauthToken?
)