package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.infra.persistence.entity.OauthTokenEntity

object OauthEntityMapper {

    fun OauthTokenEntity.toOauthToken(): OauthToken {
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

    fun OauthToken.toOauthEntity(): OauthTokenEntity {
        return OauthTokenEntity(
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