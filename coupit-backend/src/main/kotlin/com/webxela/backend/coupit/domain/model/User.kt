package com.webxela.backend.coupit.domain.model

import com.webxela.backend.coupit.domain.enum.DeviceType
import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String?,
    val email: String,
    val password: String,
    val createdAt: Instant?,
    val jwtToken: String?,
    val oauthToken: OauthToken?,
    val fcmToken: String?,
    val deviceType: DeviceType
)