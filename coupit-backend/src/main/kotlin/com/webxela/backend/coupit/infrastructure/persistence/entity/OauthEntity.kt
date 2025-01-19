package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "oauth_tokens")
data class OauthEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true, unique = true)
    val accessToken: String,

    @Column(nullable = true, unique = true)
    val refreshToken: String,

    @Column(nullable = false)
    val expiresAt: String,

    @Column(nullable = false)
    val tokenType: String,

    @Column(nullable = false)
    val shortLived: Boolean,

    @Column(nullable = false, unique = true)
    val merchantId: String
)