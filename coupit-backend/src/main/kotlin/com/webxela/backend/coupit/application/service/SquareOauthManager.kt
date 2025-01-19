package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.mappper.MerchantMapper.toSquareMerchant
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.usecase.MerchantUseCase
import com.webxela.backend.coupit.domain.usecase.OauthUseCase
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import jakarta.servlet.http.HttpServletRequest
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
                "session=true"
    }

    @Transactional
    fun processSquareOauthCallback(code: String): String? {
        val oauthToken = oauthUseCase.processOauthCallback(code)
        if (oauthToken == null) {
            logger.error("Failed to get oauth token")
            return null
        }
        val merchantInfo = oauthUseCase.getMerchantInfo(
            merchantId = oauthToken.merchantId,
            accessToken = oauthToken.accessToken
        )
        if (merchantInfo == null) {
            logger.error("Failed to get merchant info during oauth")
            return null
        }
        try {
            val user = userUseCase.getUserByEmail(merchantInfo.id)
            val data = if (user == null) {
                merchantUseCase.addNewMerchant(merchantInfo.toSquareMerchant(oauthToken))
                userAuthManager.registerNewUser(
                    email = merchantInfo.id,
                    password = oauthToken.accessToken,
                    firstName = merchantInfo.businessName,
                    lastName = "",
                ).token
            } else {
                merchantUseCase.updateMerchant(merchantInfo.toSquareMerchant(oauthToken))
                val jwtToken = jwtUtils.generateToken(merchantInfo.id)
                userUseCase.updateJwtToken(merchantInfo.id, jwtToken)
                jwtToken
            }
            return data
        } catch (ex: Exception) {
            logger.error("Failed to add merchant to database", ex)
            return null
        }
    }

    @Transactional
    fun revokeSquareOauth(): String {
        try {
            val authState = SecurityContextHolder.getContext().authentication
            userAuthManager.performUserLogout()
            // Temporarily delete the merchant entirely for logout flow
            val user = authState.principal as UserDetails
            if(merchantUseCase.deleteMerchant(user.username))
                return "Successfully disconnected from Square"
            else throw RuntimeException("Failed to disconnect from Square")
        } catch (ex: Exception) {
            logger.error("Failed to revoke oauth token", ex)
            throw ApiError.InternalError("Failed to disconnect from square", ex)
        }
    }
}