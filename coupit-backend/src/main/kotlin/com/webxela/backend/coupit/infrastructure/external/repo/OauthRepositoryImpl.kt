package com.webxela.backend.coupit.infrastructure.external.repo

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.repo.OauthRepository
import com.webxela.backend.coupit.infrastructure.external.datasource.OauthTokenDataSource
import com.webxela.backend.coupit.infrastructure.external.mapper.MerchantMapper.toSquareMerchant
import com.webxela.backend.coupit.infrastructure.external.mapper.OauthTokenMapper.toOauthToken
import org.springframework.stereotype.Component

@Component
class OauthRepositoryImpl(private val oauthTokenDataSource: OauthTokenDataSource) : OauthRepository {

    override fun exchangeAuthorizationCode(code: String): OauthToken? {
        return oauthTokenDataSource.exchangeAuthorizationCode(code)?.toOauthToken()
    }

    override fun getMerchantInfo(oauthToken: OauthToken): SquareMerchant? {
        return oauthTokenDataSource.getMerchantInfo(oauthToken.merchantId, oauthToken.accessToken)
            ?.toSquareMerchant(oauthToken)
    }

    override fun exchangeRefreshToken(refreshToken: String): OauthToken? {
        return oauthTokenDataSource.exchangeRefreshToken(refreshToken)?.toOauthToken()
    }

    override fun revokeOauthToken(merchantId: String): Boolean {
        return oauthTokenDataSource.revokeOauthToken(merchantId)
    }

    override fun isWebhookFromSquare(
        body: String,
        signature: String,
        squareWebhookUrl: String,
        squareSign: String
    ): Boolean {
        return oauthTokenDataSource.isWebhookFromSquare(body, signature, squareWebhookUrl, squareSign)
    }

}