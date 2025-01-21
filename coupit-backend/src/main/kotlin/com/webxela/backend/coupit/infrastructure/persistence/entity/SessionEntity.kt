package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.Constants.SESSION_EXPIRY
import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit


@Entity
@Table(name = "session")
data class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val sessionId: String = generateUniqueIdentifier(),

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false, unique = true)
    val paymentId: String,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(SESSION_EXPIRY, ChronoUnit.MINUTES),

    @Column(nullable = false)
    val used: Boolean = false
)
