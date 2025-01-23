package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*

@Entity
@Table(name = "rewards")
data class RewardEntity(
    @Id
    val id: String = generateUniqueIdentifier(),

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
