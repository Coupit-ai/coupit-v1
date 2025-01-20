package com.webxela.backend.coupit.infrastructure.external.mapper

import com.squareup.square.models.ObtainTokenResponse
import com.webxela.backend.coupit.domain.model.OauthToken

object OauthTokenMapper {

    fun ObtainTokenResponse.toOauthToken(): OauthToken {
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