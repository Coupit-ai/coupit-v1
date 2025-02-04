package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.SpinConfig
import com.webxela.app.coupit.domain.model.SpinResult

interface SpinRepo {

    suspend fun performSpin(sessionId: String): ApiResponse<SpinResult, DataError.Remote>

    suspend fun getSpinConfig(sessionId: String): ApiResponse<SpinConfig, DataError.Remote>

    suspend fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote>
}