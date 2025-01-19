package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.repo.SpinRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpinUseCase(private val spinRepository: SpinRepository) {

    fun getSpinResultBySpinId(spinId: UUID): SpinResult? {
        return spinRepository.getSpinResultBySpinId(spinId)
    }

    fun getSpinResultBySessionId(sessionId: UUID): SpinResult? {
        return spinRepository.getSpinResultBySessionId(sessionId)
    }

    fun saveSpinResult(merchantId: String, rewardId: UUID, qrCode: String, sessionId: UUID): SpinResult {
        return spinRepository.saveSpinResult(merchantId, rewardId, qrCode, sessionId)
    }

    fun markSpinAsClaimed(spinId: UUID): Boolean {
        return spinRepository.markSpinAsClaimed(spinId)
    }

    fun deleteSpinResult(sessionId: UUID): Boolean {
        return spinRepository.deleteSpinResult(sessionId)
    }
}