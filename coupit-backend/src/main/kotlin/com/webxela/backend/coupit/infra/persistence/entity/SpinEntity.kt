package com.webxela.backend.coupit.infra.persistence.entity

import com.webxela.backend.coupit.domain.enum.RewardState
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "spins")
data class SpinEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

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
    val expiresAt: Instant,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val rewardState: RewardState = RewardState.UNUSED

)