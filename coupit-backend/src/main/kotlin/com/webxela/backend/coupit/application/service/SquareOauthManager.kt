package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.api.rest.dto.auth.RevokeWebhookRequest
import com.webxela.backend.coupit.api.rest.mappper.PaymentMapper.toPayment
import com.webxela.backend.coupit.api.rest.mappper.SignupMapper.toSignupRequest
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.usecase.MerchantUseCase
import com.webxela.backend.coupit.domain.usecase.OauthUseCase
import com.webxela.backend.coupit.domain.usecase.PaymentUseCase
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class SquareOauthManager(
    private val squareConfig: SquareConfig,
    private val oauthUseCase: OauthUseCase,
    private val merchantUseCase: MerchantUseCase,
    private val userAuthManager: UserAuthManager,
    private val userUseCase: UserUseCase,
    private val jwtUtils: JwtUtils,
    private val utilityService: UtilityService,
    private val paymentUseCase: PaymentUseCase
) {

    companion object {
        private val logger = LogManager.getLogger(SquareOauthManager::class.java)
    }

    fun buildSquareOauthUrl(): String {
        return "${squareConfig.authorisationUri}?" +
                "client_id=${squareConfig.clientId}&" +
                "response_type=code&" +
                "scope=${squareConfig.scopes.joinToString(",")}&" +
                "redirect_uri=${squareConfig.redirectUri}&" +
                "session=false"
    }

    @Transactional
    fun processSquareOauthCallback(code: String): String? {
        val oauthToken = oauthUseCase.exchangeAuthorizationCode(code)
        if (oauthToken == null) {
            logger.error("Failed to get oauth token")
            return null
        }
        val merchantInfo = oauthUseCase.getMerchantInfo(oauthToken = oauthToken)
        if (merchantInfo == null) {
            logger.error("Failed to get merchant info during oauth")
            return null
        }
        try {
            val user = userUseCase.getUserByEmail(merchantInfo.merchantId)
            val data = if (user == null) {
                merchantUseCase.addNewMerchant(merchantInfo)
                userAuthManager.registerNewUser(
                    merchantInfo.toSignupRequest()
                ).token
            } else {
                val jwtToken = jwtUtils.generateToken(merchantInfo.merchantId)
                userUseCase.updateJwtToken(merchantInfo.merchantId, jwtToken)
                merchantUseCase.updateMerchant(merchantInfo)
                jwtToken
            }
            return data
        } catch (ex: Exception) {
            logger.error("Failed to add merchant to database", ex)
            return null
        }
    }

    @Transactional
    fun exchangeRefreshToken(refreshToken: String): Boolean {
        val oauthToken = oauthUseCase.exchangeRefreshToken(refreshToken)
        if (oauthToken == null) {
            logger.error("Failed to get refresh token")
            return false
        }
        val merchant = merchantUseCase.getMerchantById(oauthToken.merchantId)
        if (merchant == null) {
            logger.error("Failed to get merchant data while fetching refresh token")
            return false
        }
        val newMerchant = merchant.copy(oauthToken = oauthToken)
        merchantUseCase.updateMerchant(newMerchant)
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
            merchantUseCase.deleteMerchant(requestBody.merchantId)
            userUseCase.updateJwtToken(requestBody.merchantId, null)
            logger.info("Successfully disconnected from square using webhook")
        } else {
            logger.error("Received invalid revoke webhook")
            throw ApiError.Unauthorized("Invalid webhook")
        }
    }

    @Transactional
    fun revokeSquareOauth(): String {
        try {
            userAuthManager.performUserLogout()
            val merchantId = utilityService.getCurrentLoginUser()?.username
                ?: throw RuntimeException("Failed to get merchant id from this auth context")

            if (oauthUseCase.revokeOauthToken(merchantId))
                logger.info("Successfully disconnected from Square dashboard")

            if (merchantUseCase.deleteMerchant(merchantId)) {
                logger.info("Successfully disconnected from Square")
                return "Successfully disconnected from Square"
            } else throw RuntimeException("Failed to disconnect from Square")
        } catch (ex: Exception) {
            logger.error("Failed to revoke oauth token", ex)
            throw ApiError.Unauthorized("Failed to disconnect from square", ex)
        }
    }

    @Transactional
    fun handlePaymentWebhook(
        requestBody: PaymentWebhookRequest,
        signature: String
    ) {
        try {
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
                paymentUseCase.savePayment(requestBody.toPayment())
                logger.info("Received payment webhook from square")
                // Send FCM notification to merchant
            } else {
                logger.error("Received invalid payment webhook")
                return
            }
        } catch (ex: DataIntegrityViolationException) {
            logger.error("Cant save multiple instance of same payment", ex)
        } catch (ex: Exception) {
            logger.error("Failed to handle payment webhook", ex)
            throw ApiError.InternalError("Failed to handle payment webhook", ex)
        }
    }

}