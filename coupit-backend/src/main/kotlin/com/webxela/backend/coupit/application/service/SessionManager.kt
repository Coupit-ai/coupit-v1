package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.api.rest.mappper.OfferRespMapper.toOfferResponse
import com.webxela.backend.coupit.api.rest.mappper.SessionRespMapper.toSessionResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.usecase.OfferUseCase
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SessionManager(
    private val sessionUseCase: SessionUseCase,
    private val offerUseCase: OfferUseCase
) {

    private val logger = LogManager.getLogger(SessionManager::class.java)

    fun createSession(merchantId: String, transactionId: String): SessionResponse {
        try {

            val sessionData = sessionUseCase.getSessionByTransactionId(transactionId) ?: run {
                return@run sessionUseCase.createSession(merchantId, transactionId)
            }
            val offers = offerUseCase.getAllOffers(merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Internal error while creating session: ", ex)
            if (sessionUseCase.deleteSessionByTransactionId(transactionId))
                logger.error("Rollback Session")
            throw ApiError.InternalError(ex, message = "Failed to create session.")
        }
    }

    fun getSession(sessionId: UUID): SessionResponse {
        try {

            val sessionData = sessionUseCase.getSessionBySessionId(sessionId)
                ?: throw ApiError.ResourceNotFound(message = "Session with id $sessionId does not exist.")
            val offers = offerUseCase.getAllOffers(sessionData.merchantId).map { it.toOfferResponse() }
            return sessionData.toSessionResponse(offers)

        } catch (ex: Exception) {
            logger.error("Internal error while getting session: ", ex)
            throw ApiError.InternalError(ex, message = "Failed to get session.")
        }
    }

}