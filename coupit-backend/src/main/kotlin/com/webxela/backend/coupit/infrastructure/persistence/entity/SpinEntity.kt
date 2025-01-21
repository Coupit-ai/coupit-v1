package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.Constants.OFFER_EXPIRY
import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "spins")
data class SpinEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val spinId: String = generateUniqueIdentifier(),

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    val reward: RewardEntity,

    @Column(nullable = false, unique = true)
    val sessionId: String,

    @Column(nullable = false, unique = true)
    val qrCode: String,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(OFFER_EXPIRY, ChronoUnit.DAYS),

    @Column(nullable = false)
    val claimed: Boolean = false

)