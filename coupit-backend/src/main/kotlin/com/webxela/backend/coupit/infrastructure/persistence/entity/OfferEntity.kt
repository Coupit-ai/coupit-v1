package com.webxela.backend.coupit.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "offer")
data class OfferEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    val title: String,
    @Column(nullable = false, unique = true)
    val description: String
)
