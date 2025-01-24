package com.webxela.backend.coupit.infra.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "merchants")
data class MerchantEntity(
    @Id
    val id: String,

    @OneToMany(mappedBy = "merchant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val sessions: MutableSet<SessionEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "merchant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val rewards: MutableSet<RewardEntity> = mutableSetOf(),

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
    val createdAt: String
)
