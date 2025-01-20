package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.repo.OauthRepository
import org.springframework.stereotype.Component

@Component
class OauthUseCase(private val oauthRepository: OauthRepository) {

    fun exchangeAuthorizationCode(code: String): OauthToken? {
        return oauthRepository.exchangeAuthorizationCode(code)
    }

    fun getMerchantInfo(oauthToken: OauthToken): SquareMerchant? {
        return oauthRepository.getMerchantInfo(oauthToken)
    }

    fun exchangeRefreshToken(refreshToken: String): OauthToken? {
        return oauthRepository.exchangeRefreshToken(refreshToken)
    }

    fun revokeOauthToken(merchantId: String): Boolean {
        return oauthRepository.revokeOauthToken(merchantId)
    }
}