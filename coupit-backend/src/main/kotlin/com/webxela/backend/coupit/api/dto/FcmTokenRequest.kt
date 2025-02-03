package com.webxela.backend.coupit.api.dto

import com.webxela.backend.coupit.domain.enum.DeviceType

data class FcmTokenRequest(
    val token: String,
    val deviceType: DeviceType
)
