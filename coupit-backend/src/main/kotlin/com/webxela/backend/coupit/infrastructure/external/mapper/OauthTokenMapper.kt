package com.webxela.backend.coupit.infrastructure.external.mapper

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.infrastructure.external.dto.OauthTokenDto

object OauthTokenMapper {

    fun OauthTokenDto.toOauthToken(): OauthToken {
        return OauthToken(
            accessToken = this.accessToken,
            refreshToken = this.refreshToken,
            expiresAt = this.expiresAt,
            tokenType = this.tokenType,
            shortLived = this.shortLived,
            merchantId = this.merchantId
        )
    }
}