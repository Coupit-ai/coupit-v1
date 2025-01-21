package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class PaymentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = true)
    val createdAt: String?,

    @Column(nullable = true)
    val amount: String?,

    @Column(nullable = true)
    val currency: String?,

    @Column(nullable = false, unique = true)
    val paymentId: String,

    @Column(nullable = true)
    val locationId: String?,

    @Column(nullable = true)
    val orderId: String?
)