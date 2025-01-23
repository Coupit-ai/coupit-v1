package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id
    val id: String,

    @Column(nullable = false)
    val merchantId: String,

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