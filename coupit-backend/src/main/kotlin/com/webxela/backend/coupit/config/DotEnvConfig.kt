package com.webxela.backend.coupit.config

import com.squareup.square.Environment
import com.squareup.square.SquareClient
import com.squareup.square.authentication.BearerAuthModel
import com.webxela.backend.coupit.utils.AppConstants
import io.github.cdimascio.dotenv.dotenv
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Configuration

@Configuration
class DotEnvConfig {

    companion object {
        private val logger = LogManager.getLogger(DotEnvConfig::class.java)
    }

    private final val dotEnv = dotenv()

    val scopes = AppConstants.SQUARE_CLIENT_SCOPES

    final val serverUrl = dotEnv["SERVER_URL"]
        ?: throw IllegalStateException("Environment variable SERVER_URL is missing")

    val clientId: String = dotEnv["SQUARE_OAUTH_CLIENT_ID"]
        ?: throw IllegalStateException("Environment variable SQUARE_OAUTH_CLIENT_ID is missing")

    val clientSecret: String = dotEnv["SQUARE_OAUTH_CLIENT_SECRET"]
        ?: throw IllegalStateException("Environment variable SQUARE_OAUTH_CLIENT_SECRET is missing")

    val redirectUri: String = "$serverUrl/v1/square/oauth/callback"

    val revokeOauthSign: String = dotEnv["SQUARE_REVOKE_OAUTH_SIGN"]
        ?: throw IllegalStateException("Environment variable SQUARE_WEBHOOK_SIGN is missing")
    val revokeWebhookUrl = "$serverUrl/v1/square/webhook/revoke"

    val paymentWebhookSign: String = dotEnv["SQUARE_PAYMENT_WEBHOOK_SIGN"]
        ?: throw IllegalStateException("Environment variable SQUARE_PAYMENT_WEBHOOK_SIGN is missing")
    val paymentWebhookUrl = "$serverUrl/v1/square/webhook/payment"

    val clientEnvironment: Environment by lazy {
        when (dotEnv["SQUARE_ENVIRONMENT"]?.lowercase()) {
            "sandbox" -> Environment.SANDBOX
            "production" -> Environment.PRODUCTION
            else -> {
                logger.error("Invalid or missing SQUARE_ENVIRONMENT value")
                throw IllegalStateException("Failed to initialize Square client environment")
            }
        }
    }

    private val baseUri: String
        get() = when (clientEnvironment) {
            Environment.SANDBOX -> AppConstants.SQUARE_SANDBOX_URI
            Environment.PRODUCTION -> AppConstants.SQUARE_PROD_URI
            else -> throw IllegalStateException("Unexpected environment: $clientEnvironment")
        }

    val authorisationUri: String by lazy { "$baseUri/oauth2/authorize" }
//    val tokenUri: String by lazy { "$baseUri/oauth2/token" }
//    val userInfoUri: String by lazy { "$baseUri/v2/merchants/me" }
//    val userAttribute: String = "merchant"

    fun squareClient(accessToken: String? = null): SquareClient {
        logger.info("Initializing Square Client with environment: $clientEnvironment")
        return if (accessToken == null) {
            SquareClient.Builder()
                .environment(clientEnvironment)
                .build()
        } else SquareClient.Builder()
            .bearerAuthCredentials(BearerAuthModel.Builder(accessToken).build())
            .environment(clientEnvironment)
            .build()
    }
}
