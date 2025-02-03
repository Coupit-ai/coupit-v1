package com.webxela.backend.coupit.infra.persistence.entity

import com.webxela.backend.coupit.domain.enum.DeviceType
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = true)
    val lastName: String?,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = true)
    val jwtToken: String?,

    @Column(nullable = true)
    val fcmToken: String?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    val deviceType: DeviceType = DeviceType.UNKNOWN,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "oauth_token_id")
    val oauthTokenEntity: OauthTokenEntity?
)
