package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.repo.SpinRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpinUseCase(private val spinRepository: SpinRepository) {

    fun getSpinResultBySpinId(spinId: String): SpinResult? {
        return spinRepository.getSpinResultBySpinId(spinId)
    }

    fun getSpinResultBySessionId(sessionId: String): SpinResult? {
        return spinRepository.getSpinResultBySessionId(sessionId)
    }

    fun saveSpinResult(merchantId: String, rewardId: String, qrCode: String, sessionId: String): SpinResult {
        return spinRepository.saveSpinResult(merchantId, rewardId, qrCode, sessionId)
    }

    fun markSpinAsClaimed(spinId: String): Boolean {
        return spinRepository.markSpinAsClaimed(spinId)
    }

    fun deleteSpinResult(sessionId: String): Boolean {
        return spinRepository.deleteSpinResult(sessionId)
    }
}