package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.api.rest.mappper.OfferRespMapper.toOfferResponse
import com.webxela.backend.coupit.api.rest.mappper.SessionRespMapper.toSessionResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.usecase.OfferUseCase
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class SessionManager(
    private val sessionUseCase: SessionUseCase,
    private val offerUseCase: OfferUseCase
) {

    private val logger = LogManager.getLogger(this.javaClass)

    fun createSession(merchantId: String, transactionId: String): SessionResponse {
        // Merchant id should not be blank
        if (merchantId.isBlank()) {
            throw ApiError.InvalidRequest(message = "Merchant ID cannot be empty.")
        }

        // Transaction id should not be blank
        if (transactionId.isBlank()) {
            throw ApiError.InvalidRequest(message = "Transaction ID cannot be empty.")
        }

        try {
            // Early return session data if it already exists otherwise create new one
            val sessionData = sessionUseCase.getSessionByTransactionId(transactionId, merchantId) ?: run {
                return@run sessionUseCase.createSession(merchantId, transactionId)
            }

            val offers = offerUseCase.getAllOffers(merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Error while creating session for merchant $merchantId, and transaction $transactionId: ", ex)
            throw ApiError.InternalError(ex, message = "Failed to create session.")
        }
    }

    fun getSession(sessionId: UUID): SessionResponse {
        // Session should not be null
        val sessionData = sessionUseCase.getSessionBySessionId(sessionId)
            ?: throw ApiError.ResourceNotFound(message = "Session with session_id $sessionId does not exist.")

        try {
            val offers = offerUseCase.getAllOffers(sessionData.merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Internal error while getting session: ", ex)
            throw ApiError.InternalError(ex, message = "Failed to get session.")
        }
    }

}