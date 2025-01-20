package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.usecase.MerchantUseCase
import com.webxela.backend.coupit.domain.usecase.OauthUseCase
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SquareOauthManager(
    private val squareConfig: SquareConfig,
    private val oauthUseCase: OauthUseCase,
    private val merchantUseCase: MerchantUseCase,
    private val userAuthManager: UserAuthManager,
    private val userUseCase: UserUseCase,
    private val jwtUtils: JwtUtils
) {

    companion object {
        private val logger = LogManager.getLogger(SquareOauthManager::class.java)
    }

    fun buildSquareAuthUrl(): String {
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
                    email = merchantInfo.merchantId,
                    password = oauthToken.accessToken,
                    firstName = merchantInfo.businessName,
                    lastName = "",
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

    @Transactional
    fun revokeSquareOauth(merchantId: String? = null): String {
        try {
            val id = if (merchantId == null) {
                val authState = SecurityContextHolder.getContext().authentication
                userAuthManager.performUserLogout()
                (authState.principal as UserDetails).username
            } else {
                merchantId
            }

            if (oauthUseCase.revokeOauthToken(id))
                logger.info("Successfully disconnected from Square dashboard")

            if (merchantUseCase.deleteMerchant(id)) {
                logger.info("Successfully disconnected from Square")
                return "Successfully disconnected from Square"
            } else throw RuntimeException("Failed to disconnect from Square")
        } catch (ex: Exception) {
            logger.error("Failed to revoke oauth token", ex)
            throw ApiError.Unauthorized("Failed to disconnect from square", ex)
        }
    }
}