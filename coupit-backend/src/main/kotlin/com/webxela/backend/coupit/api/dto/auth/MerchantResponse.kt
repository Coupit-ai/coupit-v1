package com.webxela.backend.coupit.api.dto.auth

data class MerchantResponse(
    val id: String,
    val country: String,
    val businessName: String,
    val languageCode: String,
    val currency: String,
    val status: String,
    val mainLocationId: String,
    val createdAt: String
)
