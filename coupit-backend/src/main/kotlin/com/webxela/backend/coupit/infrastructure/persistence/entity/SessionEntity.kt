package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.Constants.SESSION_EXPIRY
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID


@Entity
@Table(name = "session")
data class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val sessionId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val timeStamp: Instant = Instant.now()
        .truncatedTo(ChronoUnit.SECONDS),

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false)
    val transactionId: String,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(SESSION_EXPIRY, ChronoUnit.MINUTES)
        .truncatedTo(ChronoUnit.SECONDS),

    @Column(nullable = false)
    val used: Boolean = false
)
