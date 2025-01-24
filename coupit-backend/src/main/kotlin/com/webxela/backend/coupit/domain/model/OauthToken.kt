package com.webxela.backend.coupit.domain.model

import java.util.UUID

data class OauthToken(
    val id: UUID? = null,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val tokenType: String,
    val shortLived: Boolean,
    val merchantId: String
)