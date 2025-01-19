package com.webxela.backend.coupit.domain.model


data class SquareMerchant(
    val id: Long? = null,
    val merchantId: String,
    val country: String,
    val businessName: String,
    val languageCode: String,
    val currency: String,
    val status: String,
    val mainLocationId: String,
    val createdAt: String,
    val oauthToken: OauthToken
)

