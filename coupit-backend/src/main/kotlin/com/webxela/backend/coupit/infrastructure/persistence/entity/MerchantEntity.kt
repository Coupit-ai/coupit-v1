package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "merchants")
data class MerchantEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, updatable = false)
    val merchantId: String,

    @Column(nullable = false)
    val country: String,

    @Column(nullable = false)
    val businessName: String,

    @Column(nullable = false)
    val languageCode: String,

    @Column(nullable = false)
    val currency: String,

    @Column(nullable = false)
    val status: String,

    @Column(nullable = false)
    val mainLocationId: String,

    @Column(nullable = false)
    val createdAt: String,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_token_id", referencedColumnName = "id")
    val oauthEntity: OauthEntity
)
