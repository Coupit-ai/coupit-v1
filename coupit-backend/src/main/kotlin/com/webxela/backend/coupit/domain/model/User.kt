package com.webxela.backend.coupit.domain.model

import java.time.Instant

data class User(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val createdAt: Instant? = null
)