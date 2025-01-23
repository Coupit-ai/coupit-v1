package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.enum.SessionState
import com.webxela.backend.coupit.common.utils.Constants.SESSION_EXPIRY
import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit


@Entity
@Table(name = "session")
data class SessionEntity(
    @Id
    val id: String = generateUniqueIdentifier(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
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
    @Enumerated(EnumType.STRING)
    val sessionState: SessionState = SessionState.ACTIVE,

    @OneToOne(mappedBy = "session", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "spin_id")
    val spin: SpinEntity? = null
)
