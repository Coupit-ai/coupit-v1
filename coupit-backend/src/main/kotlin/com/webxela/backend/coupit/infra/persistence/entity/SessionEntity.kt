package com.webxela.backend.coupit.infra.persistence.entity

import com.webxela.backend.coupit.utils.Constants.SESSION_EXPIRY
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Entity
@Table(name = "session")
data class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "payment_id", nullable = false)
    val payment: PaymentEntity,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    val merchant: MerchantEntity,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(SESSION_EXPIRY, ChronoUnit.MINUTES),

    @Column(nullable = false)
    val used: Boolean = false
)
