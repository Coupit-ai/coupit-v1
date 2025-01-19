package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.mappper.MerchantMapper.toSquareMerchant
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.usecase.MerchantUseCase
import com.webxela.backend.coupit.domain.usecase.OauthUseCase
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import com.webxela.backend.coupit.infrastructure.config.SquareConfig
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class SquareOauthManager(
    private val squareConfig: SquareConfig,
    private val oauthUseCase: OauthUseCase,
    private val merchantUseCase: MerchantUseCase,
    private val userAuthManager: UserAuthManager,
    private val session: HttpSession,
    private val userUseCase: UserUseCase,
    private val jwtUtils: JwtUtils
) {

    companion object {
        private val logger = LogManager.getLogger(SquareOauthManager::class.java)
    }

    fun buildSquareAuthUrl(): String {
        val state = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(SecureRandom.getSeed(32))
        session.setAttribute("oauth_state", state)
        return "${squareConfig.authorisationUri}?" +
                "client_id=${squareConfig.clientId}&" +
                "response_type=code&" +
                "scope=${squareConfig.scopes.joinToString(",")}&" +
                "redirect_uri=${squareConfig.redirectUri}&" +
                "state=$state&" +
                "session=false"
    }

    @Transactional
    fun processSquareOauthCallback(code: String, state: String): String? {
        val savedState = session.getAttribute("oauth_state")
        if (savedState != state) {
            logger.error("State mismatch during oauth")
            throw Exception("CSRF detected")
        }
        session.removeAttribute("oauth_state")
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
                jwtUtils.generateToken(merchantInfo.id)
            }
            return data
        } catch (ex: Exception) {
            logger.error("Failed to add merchant to database", ex)
            return null
        }
    }

}