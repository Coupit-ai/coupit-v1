package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.domain.model.SpinResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SpinRepo {

    @OptIn(ExperimentalUuidApi::class)
    fun performSpin(merchantId: String, sessionId: Uuid): SpinResult

    @OptIn(ExperimentalUuidApi::class)
    fun getSpinResult(spinId: Uuid): SpinResult
}