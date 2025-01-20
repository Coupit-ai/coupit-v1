package com.webxela.backend.coupit.infrastructure.external.datasource

import com.squareup.square.models.Merchant
import com.squareup.square.models.ObtainTokenRequest
import com.squareup.square.models.ObtainTokenResponse
import com.squareup.square.models.RevokeTokenRequest
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class OauthTokenDataSource(private val squareConfig: SquareConfig) {

    companion object {
        val logger: Logger = LogManager.getLogger(OauthTokenDataSource::class.java)
    }

    fun exchangeAuthorizationCode(code: String): ObtainTokenResponse? {
        val body = ObtainTokenRequest.Builder(squareConfig.clientId, "authorization_code")
            .code(code)
            .clientSecret(squareConfig.clientSecret)
            .redirectUri(squareConfig.redirectUri)
            .shortLived(false)
            .build()

        val client = squareConfig.squareClient()
        return try {
            client.oAuthApi.obtainToken(body)
        } catch (ex: Exception) {
            logger.error("Failed to exchange authorization code with square", ex)
            null
        }
    }

    fun getMerchantInfo(merchantId: String, accessToken: String): Merchant? {
        val client = squareConfig.squareClient(accessToken)
        return try {
            client.merchantsApi.retrieveMerchant(merchantId).merchant
        } catch (ex: Exception) {
            logger.error("Failed to retrieve merchant info from square", ex)
            null
        }
    }

    fun exchangeRefreshToken(refreshToken: String): ObtainTokenResponse? {
        val body = ObtainTokenRequest.Builder(squareConfig.clientId, "refresh_token")
            .refreshToken(refreshToken)
            .clientSecret(squareConfig.clientSecret)
            .redirectUri(squareConfig.redirectUri)
            .shortLived(false)
            .build()

        val client = squareConfig.squareClient()
        return try {
            client.oAuthApi.obtainToken(body)
        } catch (ex: Exception) {
            logger.error("Failed to exchange refresh token with square", ex)
            null
        }
    }

    fun revokeOauthToken(merchantId: String): Boolean {
        val body = RevokeTokenRequest.Builder()
            .clientId(squareConfig.clientId)
            .merchantId(merchantId)
            .revokeOnlyAccessToken(false)
            .build()

        val client = squareConfig.squareClient()
        return try {
            val response = client.oAuthApi.revokeToken(body, "Client ${squareConfig.clientSecret}")
            response.success
        } catch (ex: Exception) {
            false
        }
    }

}