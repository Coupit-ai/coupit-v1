package com.webxela.backend.coupit.infrastructure.config

import com.squareup.square.Environment
import com.squareup.square.SquareClient
import com.squareup.square.authentication.BearerAuthModel
import com.webxela.backend.coupit.common.utils.Constants
import io.github.cdimascio.dotenv.dotenv
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Configuration

@Configuration
class SquareConfig {

    companion object {
        private val logger = LogManager.getLogger(SquareConfig::class.java)
    }

    private final val dotEnv = dotenv()

    val scopes = Constants.SQUARE_CLIENT_SCOPES
    val clientId: String = dotEnv["SQUARE_OAUTH_CLIENT_ID"]
        ?: throw IllegalStateException("Environment variable SQUARE_OAUTH_CLIENT_ID is missing")

    val clientSecret: String = dotEnv["SQUARE_OAUTH_CLIENT_SECRET"]
        ?: throw IllegalStateException("Environment variable SQUARE_OAUTH_CLIENT_SECRET is missing")

    val redirectUri: String = dotEnv["SQUARE_OAUTH_REDIRECT_URI"]
        ?.let { "$it/api/v1/square/oauth/callback" }
        ?: throw IllegalStateException("Environment variable SQUARE_OAUTH_REDIRECT_URI is missing")

    private val clientEnvironment: Environment by lazy {
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
            Environment.SANDBOX -> Constants.SQUARE_SANDBOX_URI
            Environment.PRODUCTION -> Constants.SQUARE_PROD_URI
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
