package com.webxela.backend.coupit.infrastructure.external.repo

import com.squareup.square.models.Merchant
import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.repo.OauthRepository
import com.webxela.backend.coupit.infrastructure.external.datasource.OauthTokenDataSource
import com.webxela.backend.coupit.infrastructure.external.mapper.OauthTokenMapper.toOauthToken
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class OauthRepositoryImpl(private val oauthTokenDataSource: OauthTokenDataSource) : OauthRepository {

    override fun processOauthCallback(code: String): OauthToken? {
        return oauthTokenDataSource.exchangeOauthToken(code)?.toOauthToken()
    }

    override fun getMerchantInfo(merchantId: String, accessToken: String): Merchant? {
        return oauthTokenDataSource.getMerchantInfo(merchantId, accessToken)
    }

}