package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.api.dto.SpinConfigResponse
import com.webxela.backend.coupit.api.mappper.SpinDtoMapper.toSpinConfig
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SessionRepoAdapter
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SpinService(
//    private val spinUseCase: SpinUseCase,
    private val sessionRepo: SessionRepoAdapter,
    private val utilityService: UtilityService,
    private val merchantRepo: MerchantRepoAdapter
//    private val rewardUseCase: RewardUseCase
) {

    companion object {
        private val logger = LogManager.getLogger(SpinService::class.java)
    }

//    @Transactional
//    fun performSpin(merchantId: String, sessionId: String): SpinResponse {
//        // Merchant id should not be blank
//        if (merchantId.isBlank()) {
//            throw ApiError.BadRequest(message = "Merchant ID cannot be empty.")
//        }
//
//        // Session should not be null
//        val session = sessionUseCase.getSessionBySessionId(sessionId)
//            ?: throw ApiError.BadRequest(message = "Session with session_id $sessionId not found.")
//
//        // Early return spin result if exists
//        spinUseCase.getSpinResultBySessionId(sessionId)?.let {
//            return it.toSpinResponse()
//        }
//
//        // Session should not be invalid
//        if (!isValidSession(session, merchantId)) {
//            throw ApiError.Unauthorized(message = "Session with session_id $sessionId is expired or invalid.")
//        }
//
//        // Session should not be already used
//        if (session.used) {
//            throw ApiError.Unauthorized(message = "Session with session_id $sessionId is already used.")
//        }
//
//        val offers = rewardUseCase.getAllOffers(merchantId)
//        // Offers should not be empty
//        if (offers.isEmpty()) {
//            throw ApiError.ResourceNotFound(message = "No offers available.")
//        }
//
//        val randomOffer = getRandomOffer(offers)
//        val qrCode = generateQrCode(randomOffer)
//
//        try {
//            val spinResult = spinUseCase.saveSpinResult(
//                merchantId = merchantId,
//                rewardId = randomOffer.rewardId,
//                qrCode = qrCode,
//                sessionId = sessionId
//            )
//            sessionUseCase.markSessionAsUsed(sessionId)
//            return spinResult.toSpinResponse()
//
//        } catch (ex: Exception) {
//            logger.error("Error while saving spin result for merchant $merchantId and session $sessionId", ex)
//            throw ApiError.InternalError(message = "Failed to perform spin.")
//        }
//    }
//
//    private fun generateQrCode(reward: Reward): String {
//        // QR code generation logic (to be implemented)
//        return "QR code is not implemented yet. > $reward" + UUID.randomUUID().toString()
//    }
//
//    private fun getRandomOffer(rewards: List<Reward>): Reward {
//        return rewards.randomOrNull()
//            ?: throw ApiError.ResourceNotFound(message = "No offer available.")
//    }
//
//    private fun isValidSession(spinSession: SpinSession, merchantId: String): Boolean {
//        if (spinSession.merchantId != merchantId) {
//            throw ApiError.BadRequest(message = "Merchant Id $merchantId does not match with provided session.")
//        }
//
//        val currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
//        if (spinSession.expiresAt.isBefore(currentTime)) {
//            throw ApiError.Unauthorized(message = "Session with session_id ${spinSession.sessionId} has been expired.")
//        }
//        return true
//    }
//
//    fun getSpinResult(spinId: String): SpinResponse {
//        val spinResult = spinUseCase.getSpinResultBySpinId(spinId)
//            ?: throw ApiError.ResourceNotFound(message = "Spin result with id $spinId does not exist.")
//
//        return spinResult.toSpinResponse()
//    }

    @Transactional(readOnly = true)
    fun getSpinConfig(sessionId: UUID): SpinConfigResponse {
        // Session should exist
        val sessionData = sessionRepo.getSession(sessionId)
            ?: throw ApiError.ResourceNotFound(message = "Session with session_id $sessionId does not exist.")

        if (utilityService.getCurrentLoginUser()?.username != sessionData.merchant.id) {
            throw ApiError.BadRequest("You are not authorized to access this session.")
        }

        val merch = merchantRepo.getMerchant(sessionData.merchant.id)

        val p = merch?.rewards
        println(p)
        val rewards = merch?.rewards
        if (rewards.isNullOrEmpty()) {
            logger.error("There is no rewards configured.")
            throw ApiError.ResourceNotFound("No rewards available.")
        }
        return rewards.toSpinConfig(sessionData)

    }


}