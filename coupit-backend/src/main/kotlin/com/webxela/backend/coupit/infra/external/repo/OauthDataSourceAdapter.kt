package com.webxela.backend.coupit.infra.external.repo

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.infra.external.datasource.OauthTokenDataSource
import com.webxela.backend.coupit.infra.external.mapper.MerchantMapper.toSquareMerchant
import com.webxela.backend.coupit.infra.external.mapper.OauthTokenMapper.toOauthToken
import org.springframework.stereotype.Component

@Component
class OauthDataSourceAdapter(private val oauthTokenDataSource: OauthTokenDataSource) {

    fun exchangeAuthorizationCode(code: String): OauthToken? {
        return oauthTokenDataSource.exchangeAuthorizationCode(code)?.toOauthToken()
    }

    fun getMerchantInfo(oauthToken: OauthToken): SquareMerchant? {
        return oauthTokenDataSource.getMerchantInfo(oauthToken.merchantId, oauthToken.accessToken)
            ?.toSquareMerchant(oauthToken)
    }

    fun exchangeRefreshToken(refreshToken: String): OauthToken? {
        return oauthTokenDataSource.exchangeRefreshToken(refreshToken)?.toOauthToken()
    }

    fun revokeOauthToken(merchantId: String): Boolean {
        return oauthTokenDataSource.revokeOauthToken(merchantId)
    }

    fun isWebhookFromSquare(
        body: String, signature: String,
        squareWebhookUrl: String, squareSign: String
    ): Boolean {
        return oauthTokenDataSource.isWebhookFromSquare(
            body, signature,
            squareWebhookUrl, squareSign
        )
    }

}