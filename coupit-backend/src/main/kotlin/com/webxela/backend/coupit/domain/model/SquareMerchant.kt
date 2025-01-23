package com.webxela.backend.coupit.domain.model


data class SquareMerchant(
    val id: String,
    val sessions: List<SpinSession>,
    val rewards: List<Reward>,
    val country: String,
    val businessName: String,
    val languageCode: String,
    val currency: String,
    val status: String,
    val mainLocationId: String,
    val createdAt: String,
//    val oauthToken: OauthToken
)

