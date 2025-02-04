package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.FcmTokenDto
import com.webxela.app.coupit.domain.model.FcmToken

object FcmTokenDtoMapper {
    fun FcmTokenDto.toFcmToken(): FcmToken {
        return FcmToken(
            token = this.data.token,
            message = this.data.message
        )
    }
}