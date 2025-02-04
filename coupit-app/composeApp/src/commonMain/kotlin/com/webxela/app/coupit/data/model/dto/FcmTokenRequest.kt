package com.webxela.app.coupit.data.model.dto

import com.webxela.app.coupit.core.domain.DeviceType
import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenRequest(
    val token: String,
    val deviceType: DeviceType
)