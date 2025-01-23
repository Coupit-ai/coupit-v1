package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.enum.RewardState
import com.webxela.backend.coupit.common.utils.Constants.OFFER_EXPIRY
import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "spins")
data class SpinEntity(
    @Id
    val id: String = generateUniqueIdentifier(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    val session: SessionEntity,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", nullable = false)
    val reward: RewardEntity,

    @Column(nullable = false, unique = true)
    val qrCode: String,

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now()
        .plus(OFFER_EXPIRY, ChronoUnit.DAYS),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val rewardState: RewardState = RewardState.UNUSED

)