package com.webxela.backend.coupit.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty


data class OauthTokenDto(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_at")
    val expiresAt: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("short_lived")
    val shortLived: Boolean,
    @JsonProperty("merchant_id")
    val merchantId: String
)