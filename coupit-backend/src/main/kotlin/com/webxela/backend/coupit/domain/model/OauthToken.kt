package com.webxela.backend.coupit.domain.model

data class OauthToken(
    val id: Long? = null,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val tokenType: String,
    val shortLived: Boolean,
    val merchantId: String
)