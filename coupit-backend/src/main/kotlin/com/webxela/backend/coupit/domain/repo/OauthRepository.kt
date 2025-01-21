package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant

interface OauthRepository {

    fun exchangeAuthorizationCode(code: String): OauthToken?

    fun getMerchantInfo(oauthToken: OauthToken): SquareMerchant?

    fun exchangeRefreshToken(refreshToken: String): OauthToken?

    fun revokeOauthToken(merchantId: String): Boolean

    fun isWebhookFromSquare(
        body: String,
        signature: String,
        squareWebhookUrl: String,
        squareSign: String
    ): Boolean
}