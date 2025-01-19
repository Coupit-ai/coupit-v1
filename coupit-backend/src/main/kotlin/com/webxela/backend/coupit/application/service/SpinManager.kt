package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.api.rest.mappper.SpinRespMapper.toSpinResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.usecase.RewardUseCase
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import com.webxela.backend.coupit.domain.usecase.SpinUseCase
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class SpinManager(
    private val spinUseCase: SpinUseCase,
    private val sessionUseCase: SessionUseCase,
    private val rewardUseCase: RewardUseCase
) {

    companion object {
        private val logger = LogManager.getLogger(SpinManager::class.java)
    }

    @Transactional
    fun performSpin(merchantId: String, sessionId: UUID): SpinResponse {
        // Merchant id should not be blank
        if (merchantId.isBlank()) {
            throw ApiError.BadRequest(message = "Merchant ID cannot be empty.")
        }

        // Session should not be null
        val session = sessionUseCase.getSessionBySessionId(sessionId)
            ?: throw ApiError.BadRequest(message = "Session with session_id $sessionId not found.")

        // Early return spin result if exists
        spinUseCase.getSpinResultBySessionId(sessionId)?.let {
            return it.toSpinResponse()
        }

        // Session should not be invalid
        if (!isValidSession(session, merchantId)) {
            throw ApiError.Unauthorized(message = "Session with session_id $sessionId is expired or invalid.")
        }

        // Session should not be already used
        if (session.used) {
            throw ApiError.Unauthorized(message = "Session with session_id $sessionId is already used.")
        }

        val offers = rewardUseCase.getAllOffers(merchantId)
        // Offers should not be empty
        if (offers.isEmpty()) {
            throw ApiError.ResourceNotFound(message = "No offers available.")
        }

        val randomOffer = getRandomOffer(offers)
        val qrCode = generateQrCode(randomOffer)

        try {
            val spinResult = spinUseCase.saveSpinResult(
                merchantId = merchantId,
                rewardId = randomOffer.rewardId,
                qrCode = qrCode,
                sessionId = sessionId
            )
            sessionUseCase.markSessionAsUsed(sessionId)
            return spinResult.toSpinResponse()

        } catch (ex: Exception) {
            logger.error("Error while saving spin result for merchant $merchantId and session $sessionId", ex)
            throw ApiError.InternalError(message = "Failed to perform spin.")
        }
    }

    private fun generateQrCode(reward: Reward): String {
        // QR code generation logic (to be implemented)
        return "QR code is not implemented yet. > $reward" + UUID.randomUUID().toString()
    }

    private fun getRandomOffer(rewards: List<Reward>): Reward {
        return rewards.randomOrNull()
            ?: throw ApiError.ResourceNotFound(message = "No offer available.")
    }

    private fun isValidSession(session: Session, merchantId: String): Boolean {
        if (session.merchantId != merchantId) {
            throw ApiError.BadRequest(message = "Merchant Id $merchantId does not match with provided session.")
        }

        val currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        if (session.expiresAt.isBefore(currentTime)) {
            throw ApiError.Unauthorized(message = "Session with session_id ${session.sessionId} has been expired.")
        }
        return true
    }

    fun getSpinResult(spinId: UUID): SpinResponse {
        val spinResult = spinUseCase.getSpinResultBySpinId(spinId)
            ?: throw ApiError.ResourceNotFound(message = "Spin result with id $spinId does not exist.")

        return spinResult.toSpinResponse()
    }

}