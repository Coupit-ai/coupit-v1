package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.infrastructure.persistence.entity.OauthEntity

object OauthEntityMapper {

    fun OauthEntity.toSquareOauth(): OauthToken {
        return OauthToken(
            id = this.id,
            accessToken = this.accessToken,
            refreshToken = this.refreshToken,
            expiresAt = this.expiresAt,
            tokenType = this.tokenType,
            shortLived = this.shortLived,
            merchantId = this.merchantId
        )
    }

    fun OauthToken.toOauthEntity(): OauthEntity {
        return OauthEntity(
            id = this.id,
            accessToken = this.accessToken,
            refreshToken = this.refreshToken,
            expiresAt = this.expiresAt,
            tokenType = this.tokenType,
            shortLived = this.shortLived,
            merchantId = this.merchantId
        )
    }
}