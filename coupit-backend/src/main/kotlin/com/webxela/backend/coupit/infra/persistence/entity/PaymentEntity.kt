package com.webxela.backend.coupit.infra.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id
    val id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    val merchant: MerchantEntity,

    @Column(nullable = true)
    val createdAt: String?,

    @Column(nullable = true)
    val amount: String?,

    @Column(nullable = true)
    val currency: String?,

    @Column(nullable = true)
    val locationId: String?,

    @Column(nullable = true)
    val orderId: String?
)