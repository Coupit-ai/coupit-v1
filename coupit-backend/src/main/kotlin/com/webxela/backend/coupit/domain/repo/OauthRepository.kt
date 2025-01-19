package com.webxela.backend.coupit.domain.repo

import com.squareup.square.models.Merchant
import com.webxela.backend.coupit.domain.model.OauthToken

interface OauthRepository {

    fun processOauthCallback(code: String): OauthToken?

    fun getMerchantInfo(merchantId: String, accessToken: String): Merchant?
}