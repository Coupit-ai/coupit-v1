package com.webxela.backend.coupit.infra.persistence.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "rewards")
data class RewardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    val merchant: MerchantEntity,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val probability: Double,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val validityHours: Int
)
