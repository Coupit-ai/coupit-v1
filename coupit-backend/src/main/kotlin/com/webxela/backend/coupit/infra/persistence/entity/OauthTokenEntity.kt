package com.webxela.backend.coupit.infra.persistence.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "oauth_tokens")
data class OauthTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, updatable = true)
    val accessToken: String,

    @Column(nullable = false, updatable = true)
    val refreshToken: String,

    @Column(nullable = false)
    val expiresAt: String,

    @Column(nullable = false)
    val tokenType: String,

    @Column(nullable = false)
    val shortLived: Boolean,

    @Column(nullable = false, updatable = true)
    val merchantId: String
)