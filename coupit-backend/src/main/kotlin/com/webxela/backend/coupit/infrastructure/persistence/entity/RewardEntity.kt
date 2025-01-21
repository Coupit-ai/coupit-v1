package com.webxela.backend.coupit.infrastructure.persistence.entity

import com.webxela.backend.coupit.common.utils.generateUniqueIdentifier
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "rewards")
data class RewardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val rewardId: String = generateUniqueIdentifier(),

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String
)
