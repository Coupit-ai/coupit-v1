package com.webxela.backend.coupit.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OauthTokenRequest(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    @JsonProperty("code")
    val code: String,
    @JsonProperty("redirect_uri")
    val redirectUri: String,
    @JsonProperty("grant_type")
    val grantType: String
)