package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.SpinResult

interface SpinRepository {

    fun getSpinResultBySpinId(spinId: String): SpinResult?
    fun getSpinResultBySessionId(sessionId: String): SpinResult?
    fun saveSpinResult(merchantId: String, rewardId: String, qrCode: String, sessionId: String): SpinResult
    fun markSpinAsClaimed(spinId: String): Boolean
    fun deleteSpinResult(sessionId: String): Boolean

}