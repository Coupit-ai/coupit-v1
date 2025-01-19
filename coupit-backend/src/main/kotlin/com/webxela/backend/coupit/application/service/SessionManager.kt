package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.api.rest.mappper.RewardRespMapper.toOfferResponse
import com.webxela.backend.coupit.api.rest.mappper.SessionRespMapper.toSessionResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.usecase.RewardUseCase
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionManager(
    private val sessionUseCase: SessionUseCase,
    private val rewardUseCase: RewardUseCase
) {

    companion object {
        private val logger = LogManager.getLogger(SessionManager::class.java)
    }

    @Transactional
    fun createSession(merchantId: String, transactionId: String): SessionResponse {
        // Merchant id should not be blank
        if (merchantId.isBlank()) {
            throw ApiError.BadRequest(message = "Merchant ID cannot be empty.")
        }

        // Transaction id should not be blank
        if (transactionId.isBlank()) {
            throw ApiError.BadRequest(message = "Transaction ID cannot be empty.")
        }

        try {
            // Early return session data if it already exists otherwise create new one
            val sessionData = sessionUseCase.getSessionByTransactionId(transactionId, merchantId) ?: run {
                return@run sessionUseCase.createSession(merchantId, transactionId)
            }

            val offers = rewardUseCase.getAllOffers(merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Error while creating session for merchant $merchantId, and transaction $transactionId: ", ex)
            throw ApiError.InternalError("Failed to create session.", ex)
        }
    }

    fun getSession(sessionId: UUID): SessionResponse {
        // Session should not be null
        val sessionData = sessionUseCase.getSessionBySessionId(sessionId)
            ?: throw ApiError.ResourceNotFound(message = "Session with session_id $sessionId does not exist.")

        try {
            val offers = rewardUseCase.getAllOffers(sessionData.merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Internal error while getting session: ", ex)
            throw ApiError.InternalError("Failed to get session.", ex)
        }
    }

}