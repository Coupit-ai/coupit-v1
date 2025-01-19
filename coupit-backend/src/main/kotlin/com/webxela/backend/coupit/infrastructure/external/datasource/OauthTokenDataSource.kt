package com.webxela.backend.coupit.infrastructure.external.datasource

import com.squareup.square.models.Merchant
import com.webxela.backend.coupit.infrastructure.config.RestTemplateConfig
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import com.webxela.backend.coupit.infrastructure.external.dto.OauthTokenDto
import com.webxela.backend.coupit.infrastructure.external.dto.OauthTokenRequest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class OauthTokenDataSource(
    private val restTemplateConfig: RestTemplateConfig,
    private val squareConfig: SquareConfig
) {

    fun exchangeOauthToken(code: String): OauthTokenDto? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val body = OauthTokenRequest(
            clientId = squareConfig.clientId,
            code = code,
            redirectUri = squareConfig.redirectUri,
            grantType = "authorization_code",
            clientSecret = squareConfig.clientSecret,
        )

        val request = HttpEntity(body, headers)

        val responseEntity = restTemplateConfig.restTemplate().exchange(
            squareConfig.tokenUri,
            HttpMethod.POST,
            request,
            OauthTokenDto::class.java
        )
        return responseEntity.body
    }

    fun getMerchantInfo(merchantId: String, accessToken: String): Merchant? {
        val client = squareConfig.squareClient(accessToken)
        return try {
            client.merchantsApi.retrieveMerchant(merchantId).merchant
        } catch (ex: Exception) {
            null
        }
    }
}