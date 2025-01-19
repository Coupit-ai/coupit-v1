package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, updatable = false)
    @CreatedDate
    val createdAt: Instant? = Instant.now(),

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = true)
    val lastName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String
)
