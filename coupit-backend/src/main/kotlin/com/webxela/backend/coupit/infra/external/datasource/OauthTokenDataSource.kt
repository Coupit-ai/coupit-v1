package com.webxela.backend.coupit.infra.external.datasource

import com.squareup.square.models.Merchant
import com.squareup.square.models.ObtainTokenRequest
import com.squareup.square.models.ObtainTokenResponse
import com.squareup.square.models.RevokeTokenRequest
import com.squareup.square.utilities.WebhooksHelper
import com.webxela.backend.coupit.config.DotEnvConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class OauthTokenDataSource(private val dotEnvConfig: DotEnvConfig) {

    companion object {
        val logger: Logger = LogManager.getLogger(OauthTokenDataSource::class.java)
    }

    fun exchangeAuthorizationCode(code: String): ObtainTokenResponse? {
        val body = ObtainTokenRequest.Builder(dotEnvConfig.clientId, "authorization_code")
            .code(code)
            .clientSecret(dotEnvConfig.clientSecret)
            .redirectUri(dotEnvConfig.redirectUri)
            .shortLived(false)
            .build()

        val client = dotEnvConfig.squareClient()
        return try {
            client.oAuthApi.obtainToken(body)
        } catch (ex: Exception) {
            logger.error("Failed to exchange authorization code with square", ex)
            null
        }
    }

    fun getMerchantInfo(merchantId: String, accessToken: String): Merchant? {
        val client = dotEnvConfig.squareClient(accessToken)
        return try {
            client.merchantsApi.retrieveMerchant(merchantId).merchant
        } catch (ex: Exception) {
            logger.error("Failed to retrieve merchant info from square: ${ex.message}", ex)
            null
        }
    }

    fun exchangeRefreshToken(refreshToken: String): ObtainTokenResponse? {
        val body = ObtainTokenRequest.Builder(dotEnvConfig.clientId, "refresh_token")
            .refreshToken(refreshToken)
            .clientSecret(dotEnvConfig.clientSecret)
            .redirectUri(dotEnvConfig.redirectUri)
            .shortLived(false)
            .build()

        val client = dotEnvConfig.squareClient()
        return try {
            client.oAuthApi.obtainToken(body)
        } catch (ex: Exception) {
            logger.error("Failed to exchange refresh token with square: ${ex.message}", ex)
            null
        }
    }

    fun revokeOauthToken(merchantId: String): Boolean {
        val body = RevokeTokenRequest.Builder()
            .clientId(dotEnvConfig.clientId)
            .merchantId(merchantId)
            .revokeOnlyAccessToken(false)
            .build()

        val client = dotEnvConfig.squareClient()
        return try {
            val response = client.oAuthApi.revokeToken(body, "Client ${dotEnvConfig.clientSecret}")
            response.success
        } catch (ex: Exception) {
            false
        }
    }

    fun isWebhookFromSquare(
        body: String, signature: String,
        squareWebhookUrl: String, squareSign: String
    ): Boolean {
        try {
            return WebhooksHelper.isValidWebhookEventSignature(
                body, signature,
                squareSign, squareWebhookUrl
            )
        } catch (ex: Exception) {
            logger.error("Failed to validate webhook signature: ${ex.message}", ex)
            return false
        }
    }

}