package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.api.dto.auth.MerchantResponse
import com.webxela.backend.coupit.api.dto.auth.RevokeWebhookRequest
import com.webxela.backend.coupit.api.mappper.MerchantMapper.toMerchantResponse
import com.webxela.backend.coupit.api.mappper.SignupDtoMapper.toSignupRequest
import com.webxela.backend.coupit.config.SquareConfig
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.external.repo.OauthDataSourceAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.JwtUtils
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class SquareOauthService(
    private val squareConfig: SquareConfig,
    private val oauthDataSource: OauthDataSourceAdapter,
    private val merchantRepo: MerchantRepoAdapter,
    private val userAuthService: UserAuthService,
    private val userRepo: UserRepoAdapter,
    private val jwtUtils: JwtUtils,
    private val utilityService: UtilityService,
) {

    companion object {
        private val logger = LogManager.getLogger(SquareOauthService::class.java)
    }

    fun buildSquareOauthUrl(state: String): String {
        return "${squareConfig.authorisationUri}?" +
                "client_id=${squareConfig.clientId}&" +
                "response_type=code&" +
                "scope=${squareConfig.scopes.joinToString(",")}&" +
                "redirect_uri=${squareConfig.redirectUri}&" +
                "session=false&" +
                "state=$state"
    }

    @Transactional
    fun processSquareOauthCallback(code: String, state: String): String? {
        val oauthToken = oauthDataSource.exchangeAuthorizationCode(code = code) ?: run {
            logger.error("Failed to get oauth token")
            return null
        }

        val merchantInfo = oauthDataSource.getMerchantInfo(oauthToken = oauthToken) ?: run {
            logger.error("Failed to get merchant info during oauth flow")
            return null
        }

        try {
            val user = userRepo.getUserByEmail(merchantInfo.id)
            val data = if (user == null) {
                // Create new user
                userAuthService.registerNewUser(merchantInfo.toSignupRequest()).token
            } else {
                // Update existing user
                val jwtToken = jwtUtils.generateToken(merchantInfo.id)
                val jwtUpdated = userRepo.updateJwtToken(merchantInfo.id, jwtToken)
                if (jwtUpdated) jwtToken
                else throw RuntimeException("Failed to update existing user")
            }

            merchantRepo.saveOrUpdateMerchant(merchantInfo)
            val oauthUpdated = userRepo.updateOauthToken(email = merchantInfo.id, oauthToken = oauthToken)
            if (oauthUpdated.not()) {
                logger.error("Failed to update oauth token")
                throw RuntimeException("Failed to update oauth token")
            }
            return data
        } catch (ex: Exception) {
            logger.error("Failed to process oauth callback: ${ex.message}", ex)
            return null
        }
    }

    @Transactional
    fun exchangeRefreshToken(refreshToken: String): Boolean {
        val oauthToken = oauthDataSource.exchangeRefreshToken(refreshToken) ?: run {
            logger.error("Failed to get refresh token")
            return false
        }

        val merchant = merchantRepo.getMerchant(merchantId = oauthToken.merchantId) ?: run {
            logger.error("Failed to get merchant data while fetching refresh token")
            return false
        }

        // Update new oauth token
        val updated = userRepo.updateOauthToken(merchant.id, oauthToken)
        if (updated.not()) {
            logger.error("Failed to exchange refresh token")
            return false
        }
        return true
    }

    // Will investigate it later
    @Transactional
    fun handleRevokeWebhook(requestBody: RevokeWebhookRequest, signature: String) {
//        val mapper = jacksonObjectMapper()
//        val stringifiedBody = mapper.writeValueAsString(requestBody)
//
//        val isFromSquare = oauthUseCase.isWebhookFromSquare(
//            stringifiedBody, signature,
//            squareConfig.revokeWebhookUrl,
//            squareConfig.revokeOauthSign
//        )
        // We are not verifying the signature for now
        val isFromSquare = true
        if (isFromSquare) {

            userRepo.updateOauthToken(requestBody.merchantId, null)
            userRepo.updateJwtToken(requestBody.merchantId, null)

            logger.info("Successfully disconnected from square using webhook")

        } else {
            logger.error("Received invalid revoke webhook")
            throw ApiError.Unauthorized("Invalid webhook")
        }
    }

    @Transactional
    fun revokeSquareOauth(): String {
        try {
            val merchantId = utilityService.getCurrentLoginUser()?.username
                ?: throw RuntimeException("Failed to get merchant id from this auth context")

            if (oauthDataSource.revokeOauthToken(merchantId))
                logger.info("Successfully disconnected from Square dashboard")

            val updatedToken = userRepo.updateOauthToken(merchantId, null)

            if (updatedToken) {

                userRepo.updateFcmToken(merchantId, null)
                userAuthService.performUserLogout()
                logger.info("Successfully disconnected from Square")
                return "Successfully disconnected from Square"

            } else throw RuntimeException("Failed to disconnect from Square")

        } catch (ex: Exception) {

            logger.error("Failed to revoke oauth token: ${ex.message}", ex)
            throw ApiError.InternalError("Failed to disconnect from square", ex)

        }
    }

    fun getLoggedInMerchant(): MerchantResponse {
        val merchantId = utilityService.getCurrentLoginUser()?.username
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val merchant = merchantRepo.getMerchant(merchantId)
            ?: throw ApiError.ResourceNotFound("Merchant not found")

        return merchant.toMerchantResponse()
    }
}