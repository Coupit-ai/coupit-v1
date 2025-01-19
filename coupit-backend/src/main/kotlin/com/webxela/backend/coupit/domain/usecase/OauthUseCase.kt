package com.webxela.backend.coupit.domain.usecase

import com.squareup.square.models.Merchant
import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.repo.OauthRepository
import org.springframework.stereotype.Component

@Component
class OauthUseCase(private val oauthRepository: OauthRepository) {

    fun processOauthCallback(code: String): OauthToken? {
        return oauthRepository.processOauthCallback(code)
    }

    fun getMerchantInfo(merchantId: String, accessToken: String): Merchant? {
        return oauthRepository.getMerchantInfo(merchantId, accessToken)
    }
}