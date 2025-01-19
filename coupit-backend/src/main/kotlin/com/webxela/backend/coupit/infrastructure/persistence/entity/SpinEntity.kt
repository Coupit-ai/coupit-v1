package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.Constants.OFFER_EXPIRY
import jakarta.persistence.*
import org.hibernate.engine.internal.Cascade
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Entity
@Table(name = "spins")
data class SpinEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val spinId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false)
    val timeStamp: Instant = Instant.now()
        .truncatedTo(ChronoUnit.SECONDS),

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    val offer: RewardEntity,

    @Column(nullable = false, unique = true)
    val sessionId: UUID,

    @Column(nullable = false, unique = true)
    val qrCode: String,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(OFFER_EXPIRY, ChronoUnit.DAYS)
        .truncatedTo(ChronoUnit.SECONDS),

    @Column(nullable = false)
    val claimed: Boolean = false

)