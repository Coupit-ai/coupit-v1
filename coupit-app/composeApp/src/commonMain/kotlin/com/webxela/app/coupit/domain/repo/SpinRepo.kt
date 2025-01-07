package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.SpinResult

interface SpinRepo {

    fun performSpin(merchantId: String, sessionId: String): ApiResponse<SpinResult, DataError.Remote>

    fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote>
}