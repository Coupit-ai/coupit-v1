package com.webxela.app.coupit.domain.model

data class Merchant(
    val id: String,
    val businessName: String,
    val country: String,
    val createdAt: String,
    val currency: String,
    val languageCode: String,
    val mainLocationId: String,
    val status: String
)