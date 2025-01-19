package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.SpinResult
import java.util.UUID

interface SpinRepository {

    fun getSpinResultBySpinId(spinId: UUID): SpinResult?
    fun getSpinResultBySessionId(sessionId: UUID): SpinResult?
    fun saveSpinResult(merchantId: String, rewardId: UUID, qrCode: String, sessionId: UUID): SpinResult
    fun markSpinAsClaimed(spinId: UUID): Boolean
    fun deleteSpinResult(sessionId: UUID): Boolean

}