package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.repo.SpinRepo
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpinUseCase(
    private val spinRepo: SpinRepo
) {

    fun getSpinResultBySpinId(spinId: UUID): SpinResult? {
        return spinRepo.getSpinResultBySpinId(spinId)
    }

    fun getSpinResultBySessionId(sessionId: UUID): SpinResult? {
        return spinRepo.getSpinResultBySessionId(sessionId)
    }

    fun saveSpinResult(merchantId: String, offerId: UUID, qrCode: String, sessionId: UUID): SpinResult {
        return spinRepo.saveSpinResult(merchantId, offerId, qrCode, sessionId)
    }

    fun markSpinAsClaimed(spinId: UUID): Boolean {
        return spinRepo.markSpinAsClaimed(spinId)
    }

    fun deleteSpinResult(sessionId: UUID): Boolean {
        return spinRepo.deleteSpinResult(sessionId)
    }
}