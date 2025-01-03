package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.api.rest.mappper.SpinRespMapper.toSpinResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.usecase.OfferUseCase
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import com.webxela.backend.coupit.domain.usecase.SpinUseCase
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class SpinManager(
    private val spinUseCase: SpinUseCase,
    private val sessionUseCase: SessionUseCase,
    private val offerUseCase: OfferUseCase
) {

    private val logger = LogManager.getLogger(this.javaClass)

    fun performSpin(merchantId: String, sessionId: UUID): SpinResponse {
        // Merchant id should not be blank
        if (merchantId.isBlank()) {
            throw ApiError.InvalidRequest(message = "Merchant ID cannot be empty.")
        }

        // Session should not be null
        val session = sessionUseCase.getSessionBySessionId(sessionId)
            ?: throw ApiError.InvalidRequest(message = "Session not found.")

        // Early return spin result if exists
        spinUseCase.getSpinResultBySessionId(sessionId)?.let {
            return it.toSpinResponse()
        }

        // Session should not be invalid
        if (!isValidSession(session, merchantId)) {
            throw ApiError.Unauthorized(message = "Session is expired or invalid.")
        }

        // Session should not be already used
        if (session.used) {
            throw ApiError.Unauthorized(message = "Session is already used.")
        }

        val offers = offerUseCase.getAllOffers(merchantId)
        // Offers should not be empty
        if (offers.isEmpty()) {
            throw ApiError.ResourceNotFound(message = "No offers available.")
        }

        val randomOffer = getRandomOffer(offers)
        val qrCode = generateQrCode(randomOffer)

        try {
            val spinResult = spinUseCase.saveSpinResult(
                merchantId = merchantId,
                offerId = randomOffer.offerId,
                qrCode = qrCode,
                sessionId = sessionId
            )
            sessionUseCase.markSessionAsUsed(sessionId)
            return spinResult.toSpinResponse()

        } catch (ex: Exception) {
            logger.error("Error while saving spin result for merchant $merchantId and session $sessionId", ex)
            if (spinUseCase.deleteSpinResult(sessionId)) logger.error("Rollback spin result")
            throw ApiError.InternalError(message = "Failed to perform spin.")
        }
    }

    private fun generateQrCode(offer: Offer): String {
        // QR code generation logic (to be implemented)
        return "QR code is not implemented yet." + UUID.randomUUID().toString()
    }

    private fun getRandomOffer(offers: List<Offer>): Offer {
        return offers.randomOrNull()
            ?: throw ApiError.ResourceNotFound(message = "No offer available.")
    }

    private fun isValidSession(session: Session, merchantId: String): Boolean {
        if (session.merchantId != merchantId) {
            throw ApiError.InvalidRequest(message = "Merchant ID does not match the session.")
        }

        val currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        if (session.expiresAt.isBefore(currentTime)) {
            throw ApiError.Unauthorized(message = "Session has expired.")
        }
        return true
    }

    fun getSpinResult(spinId: UUID): SpinResponse {
        val spinResult = spinUseCase.getSpinResultBySpinId(spinId)
            ?: throw ApiError.ResourceNotFound(message = "Spin result with id $spinId does not exist.")

        return spinResult.toSpinResponse()
    }

}